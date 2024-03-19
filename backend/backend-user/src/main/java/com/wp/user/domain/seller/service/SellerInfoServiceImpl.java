package com.wp.user.domain.seller.service;
import com.wp.user.domain.seller.dto.request.AddSellerInfoRequest;
import com.wp.user.domain.seller.dto.response.GetSellerInfoListResponse;
import com.wp.user.domain.seller.dto.response.GetSellerInfoResponse;
import com.wp.user.domain.seller.dto.response.GetSellerResponse;
import com.wp.user.domain.seller.entity.SellerInfo;
import com.wp.user.domain.seller.repository.SellerInfoRepository;
import com.wp.user.domain.user.entity.Auth;
import com.wp.user.domain.user.entity.User;
import com.wp.user.domain.user.repository.UserRepository;
import com.wp.user.global.common.code.ErrorCode;
import com.wp.user.global.common.request.AccessTokenRequest;
import com.wp.user.global.common.request.DeleteTokenRequest;
import com.wp.user.global.common.request.ExtractionRequest;
import com.wp.user.global.common.service.AuthClient;
import com.wp.user.global.common.service.JwtService;
import com.wp.user.global.exception.BusinessExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerInfoServiceImpl implements SellerInfoService {
    private final SellerInfoRepository sellerInfoRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthClient authClient;

    // 판매자 상세 정보 조회
    @Override
    public GetSellerResponse getSeller(Long sellerId) {
        GetSellerResponse getSellerResponse = sellerInfoRepository.findSellerByUserId(sellerId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER_ID));
        try {
            if(getSellerResponse.getSellerInfoId() == null) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER);
            }
            if(!getSellerResponse.getAuth().equals(Auth.SELLER)) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
            }
        } catch (BusinessExceptionHandler e) {
            throw new BusinessExceptionHandler(e.getErrorCode());
        }
        return getSellerResponse;
    }

    // 판매자 전환 신청 목록 조회
    @Override
    public GetSellerInfoListResponse getSellerInfos(HttpServletRequest httpServletRequest, int page, int size) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 관리자일 경우만 조회
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("auth")).build()).getInfos();
        try {
            if(!infos.get("auth").equals("ADMIN")) {
                throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            }
        } catch(Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerDate"));
        Page<SellerInfo> sellerInfoList = sellerInfoRepository.findAll(pageable);
        return GetSellerInfoListResponse.from(sellerInfoList.getTotalPages(), sellerInfoList.getTotalElements(), sellerInfoList.stream().toList());
    }

    // 판매자 전환 신청 상세 조회
    @Override
    public GetSellerInfoResponse getSellerInfo(HttpServletRequest httpServletRequest, Long sellerInfoId) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 관리자 & 구매자일 경우만 조회
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("auth")).build()).getInfos();
        try {
            if(infos.get("auth").equals("BUYER")) {
                throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            }
        } catch(Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
        }
        SellerInfo sellerInfo = sellerInfoRepository.findById(sellerInfoId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER));
        return GetSellerInfoResponse.builder()
                .sellerInfoId(sellerInfo.getId())
                .userId(sellerInfo.getUser().getId())
                .businessNumber(sellerInfo.getBusinessNumber())
                .businessContent(sellerInfo.getBusinessContent())
                .mailOrderSalesNumber(sellerInfo.getMailOrderSalesNumber())
                .businessAddress(sellerInfo.getBusinessAddress())
                .phoneNumber(sellerInfo.getPhoneNumber())
                .registerDate(sellerInfo.getRegisterDate())
                .approvalStatus(sellerInfo.isApprovalStatus()).build();
    }

    // 판매자 전환 신청
    @Override
    @Transactional
    public void addSellerInfo(HttpServletRequest httpServletRequest, AddSellerInfoRequest addSellerInfoRequest) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 구매자일 경우만 저장
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId", "auth")).build()).getInfos();
        try {
            if(!infos.get("auth").equals("BUYER")) {
                throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            }
        } catch(Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
        }

        User user = userRepository.findById(Long.valueOf(infos.get("userId"))).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_ID));
        SellerInfo sellerInfo = SellerInfo.builder()
                .user(user)
                .businessNumber(addSellerInfoRequest.getBusinessNumber())
                .businessContent(addSellerInfoRequest.getBusinessContent())
                .mailOrderSalesNumber(addSellerInfoRequest.getMailOrderSalesNumber())
                .businessAddress(addSellerInfoRequest.getBusinessAddress())
                .phoneNumber(addSellerInfoRequest.getPhoneNumber())
                .approvalStatus(false)
                .build();
        sellerInfoRepository.save(sellerInfo);
    }

    // 판매자 전환 신청 승인
    @Override
    @Transactional
    public void modifySellerStatusTrue(HttpServletRequest httpServletRequest, Long sellerInfoId) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        String refreshToken = jwtService.resolveRefreshToken(httpServletRequest);
        // 권한이 관리자일 경우만 승인
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("auth")).build()).getInfos();
        try {
            if(!infos.get("auth").equals("ADMIN")) {
                throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            }
        } catch(Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
        }
        SellerInfo sellerInfo = sellerInfoRepository.findById(sellerInfoId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER));
        // 이미 승인된 경우
        try {
            if(sellerInfo.isApprovalStatus()) {
                throw new BusinessExceptionHandler(ErrorCode.ALREADY_APPROVE_SELLER);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.ALREADY_APPROVE_SELLER);
        }
        sellerInfo.setApprovalStatus(true);
        sellerInfo.getUser().setAuth(Auth.SELLER);
        // 토큰 삭제
        authClient.deleteTokenByUserId(DeleteTokenRequest.builder().userId(String.valueOf(sellerInfo.getUser().getId())).build());
    }

    // 판매자 전환 신청 철회
    @Override
    @Transactional
    public void modifySellerStatusFalse(HttpServletRequest httpServletRequest, Long sellerInfoId) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 관리자일 경우만 철회
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("auth")).build()).getInfos();
        try {
            if(!infos.get("auth").equals("ADMIN")) {
                throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
            }
        } catch(Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.FORBIDDEN_ERROR);
        }
        SellerInfo sellerInfo = sellerInfoRepository.findById(sellerInfoId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER));
        // 이미 철회된 경우
        try {
            if(!sellerInfo.isApprovalStatus()) {
                throw new BusinessExceptionHandler(ErrorCode.ALREADY_CANCEL_SELLER);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.ALREADY_CANCEL_SELLER);
        }
        sellerInfo.setApprovalStatus(false);
        sellerInfo.getUser().setAuth(Auth.BUYER);
        // 토큰 삭제
        authClient.deleteTokenByUserId(DeleteTokenRequest.builder().userId(String.valueOf(sellerInfo.getUser().getId())).build());
    }
}
