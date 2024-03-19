package com.wp.user.domain.user.service;

import com.wp.user.domain.alarm.entity.AlarmToken;
import com.wp.user.domain.alarm.repository.AlarmTokenRepository;
import com.wp.user.domain.user.dto.request.*;
import com.wp.user.domain.user.dto.response.*;
import com.wp.user.domain.user.entity.Auth;
import com.wp.user.domain.user.entity.EmailCode;
import com.wp.user.domain.user.entity.User;
import com.wp.user.domain.user.repository.EmailCodeRepository;
import com.wp.user.domain.user.repository.UserRepository;
import com.wp.user.global.common.code.ErrorCode;
import com.wp.user.global.common.request.AccessTokenRequest;
import com.wp.user.global.common.request.ExtractionRequest;
import com.wp.user.global.common.request.IssueTokenRequest;
import com.wp.user.global.common.request.TokenRequest;
import com.wp.user.global.common.response.IssueTokenResponse;
import com.wp.user.global.common.service.AuthClient;
import com.wp.user.global.common.service.JwtService;
import com.wp.user.global.exception.BusinessExceptionHandler;
import com.wp.user.global.file.service.S3UploadService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailCodeRepository emailCodeRepository;
    private final AlarmTokenRepository alarmTokenRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final AuthClient authClient;
    private final S3UploadService s3UploadService;

    // 회원가입
    @Override
    @Transactional
    public void addUser(AddUserRequest addUserRequest) {

        // 이메일 중복인 경우 강제 에러 발생
        try {
            if (userRepository.existsByEmail(addUserRequest.getEmail()))
                throw new BusinessExceptionHandler(ErrorCode.ALREADY_REGISTERED_EMAIL);
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.ALREADY_REGISTERED_EMAIL); // errorCode : B001
        }
        // User 엔티티 생성
        User user = User.builder()
                .auth(Auth.BUYER)
                .loginId(addUserRequest.getLoginId())
                .password(passwordEncoder.encode(addUserRequest.getPassword()))
                .email(addUserRequest.getEmail())
                .nickname(addUserRequest.getNickname())
                .sex(addUserRequest.getSex())
                .birthday(addUserRequest.getBirthday())
                .build();

        // 회원 저장
        userRepository.save(user);
    }

    // 로그인 ID 중복 확인
    @Override
    public DuplicateLoginIdResponse getUserByLoginId(String loginId) {
        boolean isDuplicate = userRepository.existsByLoginId(loginId);
        return DuplicateLoginIdResponse.builder().isDuplicate(isDuplicate).build();
    }

    // 이메일 인증
    @Override
    @Transactional
    public CheckEmailResponse checkEmail(CheckEmailRequest checkEmailRequest) {
        // 이메일 보내기
        String verifyCode = UUID.randomUUID().toString().substring(0, 6); // 랜덤 인증번호 uuid를 이용
        StringBuffer sb = new StringBuffer();
        sb.append("안녕하세요. 멋쟁이 토마토 이메일 인증 번호 안내 관련 이메일입니다.");
        sb.append(System.lineSeparator());
        sb.append("이메일 인증 번호는 [").append(verifyCode).append("]입니다.");
        sendEmail(checkEmailRequest.getEmail(), "이메일 인증 번호", sb);
        EmailCode emailCode = EmailCode.builder().id(checkEmailRequest.getEmail()).code(verifyCode).build();
        emailCodeRepository.save(emailCode); // {key, value} 5분동안 저장.
        return CheckEmailResponse.builder().verifyCode(verifyCode).build();
    }

    // 이메일 인증 확인
    @Override
    @Transactional
    public CheckEmailVerificationResponse checkEmailVerification(String email, String code) {
        EmailCode emailCode = emailCodeRepository.findById(email)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SEND_EMAIL)); // errorCode : B009
        return CheckEmailVerificationResponse.builder().isVerify(emailCode.getCode().equals(code)).build();
    }

    // 로그인
    @Override
    @Transactional
    public LoginResponse login(LoginRequest logInRequest) {
        // 로그인, 패스워드 검사
        User user = userRepository.findUserByLoginId(logInRequest.getLoginId())
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_LOGIN_ID)); // errorCode : B002
        try {
            if (!passwordEncoder.matches(logInRequest.getPassword(), user.getPassword()))
                throw new BusinessExceptionHandler(ErrorCode.NOT_VALID_PASSWORD);
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_VALID_PASSWORD); // errorCode : B003
        }
        // 토큰 발급
        IssueTokenResponse issueTokenResponse = authClient
                .issueToken(IssueTokenRequest.builder().userId(user.getId()).auth(user.getAuth()).build());
        // 알림을 위한 토큰 Redis 저장
        alarmTokenRepository.save(AlarmToken.builder().id(user.getId()).token(logInRequest.getToken()).build());
        return LoginResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .auth(user.getAuth())
                .accessToken(issueTokenResponse.getAccessToken())
                .refreshToken(issueTokenResponse.getRefreshToken())
                .build();
    }

    // 아이디 찾기
    @Override
    @Transactional
    public void getLoginIdByEmail(String email) {
        // 이메일로 로그인 아이디 찾기
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_EMAIL)); // errorCode : B005
        // 이메일 보내기
        StringBuffer sb = new StringBuffer();
        sb.append("안녕하세요. 멋쟁이 토마토 로그인 아이디 안내 관련 이메일입니다.");
        sb.append(System.lineSeparator());
        sb.append("[").append(user.getNickname()).append("]님의 가입하신 아이디는 ").append(user.getLoginId()).append("입니다.");
        sendEmail(email, "로그인 아이디", sb);
    }

    // 비밀번호 찾기
    @Override
    @Transactional
    public void getPasswordByEmail(FindPasswordRequest findPasswordRequest) {
        // 이메일로 회원 정보 찾기
        User user = userRepository
                .findUserByLoginIdAndEmail(findPasswordRequest.getLoginId(), findPasswordRequest.getEmail())
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_LOGIN_ID_EMAIL)); // errorCode
                                                                                                           // : B007
        // 새로운 임시 비밀번호 설정
        String tempPassword = getTempPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        // 이메일 보내기
        StringBuffer sb = new StringBuffer();
        sb.append("안녕하세요. 멋쟁이 토마토 임시 비밀번호 안내 관련 이메일입니다.");
        sb.append(System.lineSeparator());
        sb.append("[").append(user.getNickname()).append("]님의 임시 비밀번호는 ").append(tempPassword).append("입니다.");
        sendEmail(user.getEmail(), "임시 비밀번호", sb);
    }

    // 임시 비밀번호 생성
    public static String getTempPassword() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        StringBuilder str = new StringBuilder();

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str.append(charSet[idx]);
        }
        return str.toString();
    }

    // 로그아웃
    @Override
    @Transactional
    public void logout(HttpServletRequest httpServletRequest) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 로그아웃 처리
        authClient.deleteToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient
                .extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build())
                .getInfos();
        // 알림을 위한 토큰 Redis 삭제
        alarmTokenRepository.deleteById(Long.valueOf(infos.get("userId")));
    }

    // 개인 회원 정보 조회
    @Override
    public GetUserResponse getUser(HttpServletRequest httpServletRequest) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient
                .extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build())
                .getInfos();
        // 회원 아이디로 회원 정보 추출
        return userRepository.findUserById(Long.valueOf(infos.get("userId")))
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_ID));
    }

    // 개인 회원 정보 수정
    @Override
    @Transactional
    public ModifyUserResponse modifyUser(HttpServletRequest httpServletRequest, MultipartFile profileImgFile,
            ModifyUserRequest modifyUserRequest) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        String refreshToken = jwtService.resolveRefreshToken(httpServletRequest);
        // 회원 정보 추출
        Map<String, String> infos = authClient
                .extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build())
                .getInfos();
        // 회원 아이디로 회원 정보 추출
        User user = userRepository.findById(Long.valueOf(infos.get("userId")))
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_ID));

        // 프로필 삭제
        if (modifyUserRequest.getProfileImg() == null) {
            user.setProfileImg(null);
        }
        // 프로필 수정
        else if (profileImgFile != null && !profileImgFile.isEmpty()) {
            // 파일 업로드
            String imgSrc = s3UploadService.saveFile(profileImgFile);
            user.setProfileImg(imgSrc);
        }
        // 닉네임, 성별, 생년월일 수정
        user.setNickname(modifyUserRequest.getNickname());
        user.setSex(modifyUserRequest.getSex());
        user.setBirthday(modifyUserRequest.getBirthday());

        ModifyUserResponse modifyUserResponse = ModifyUserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .auth(user.getAuth())
                .build();
        // 비밀번호 수정
        if (modifyUserRequest.getPassword() != null && modifyUserRequest.getNewPassword() != null) {
            try {
                if (!passwordEncoder.matches(modifyUserRequest.getPassword(), user.getPassword()))
                    throw new BusinessExceptionHandler(ErrorCode.NOT_VALID_PASSWORD);
            } catch (Exception e) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_VALID_PASSWORD); // errorCode : B003
            }
            // 토큰 재발급
            IssueTokenResponse issueTokenResponse = authClient
                    .reissueToken(TokenRequest.builder().accessToken(accessToken).refreshToken(refreshToken).build());
            modifyUserResponse.setAccessToken(issueTokenResponse.getAccessToken());
            modifyUserResponse.setRefreshToken(issueTokenResponse.getRefreshToken());
            user.setPassword(passwordEncoder.encode(modifyUserRequest.getNewPassword()));
        }
        return modifyUserResponse;
    }

    // 회원 탈퇴
    @Override
    @Transactional
    public void removeUser(HttpServletRequest httpServletRequest) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient
                .extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build())
                .getInfos();
        // 회원 탈퇴 처리(Refresh Token 삭제)
        authClient.deleteToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 탈퇴 처리(DB 삭제)
        userRepository.deleteById(Long.valueOf(infos.get("userId")));
    }

    // 전체 회원 정보 조회
    @Override
    public GetUserListResponse getUsers(HttpServletRequest httpServletRequest, int page, int size) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한 추출
        Map<String, String> infos = authClient
                .extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("auth")).build())
                .getInfos();
        try {
            if (!infos.get("auth").equals("ADMIN")) {
                throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
        }
        // 회원 정보 조회
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<User> users = userRepository.findAllByAuthNot(pageable, Auth.ADMIN);
        return GetUserListResponse.builder().totalPage(users.getTotalPages()).totalSize(users.getTotalElements())
                .users(users.stream().toList()).build();
    }

    // 회원 강제 탈퇴
    @Override
    @Transactional
    public void forceRemoveUser(HttpServletRequest httpServletRequest, Long id) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한 추출
        Map<String, String> infos = authClient
                .extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("auth")).build())
                .getInfos();
        try {
            if (!infos.get("auth").equals("ADMIN")) {
                throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
        }
        // 회원 탈퇴 처리(Refresh Token 삭제)
        authClient.deleteToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 탈퇴 처리(DB 삭제)
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void sendEmail(String email, String title, StringBuffer sb) {
        // 이메일 보내기
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(email);
            message.setFrom(new InternetAddress("MASKING_URL", "멋쟁이 토마토"));
            helper.setSubject("[멋쟁이 토마토] " + title + " 안내 이메일입니다.");
            helper.setText(sb.toString());
            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BusinessExceptionHandler(ErrorCode.SEND_EMAIL_ERROR);
        }
    }

}
