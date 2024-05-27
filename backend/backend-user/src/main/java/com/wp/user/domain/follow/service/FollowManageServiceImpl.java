package com.wp.user.domain.follow.service;

import com.wp.user.domain.follow.dto.request.AddFollowManageRequest;
import com.wp.user.domain.follow.dto.response.FollowStatusResponse;
import com.wp.user.domain.follow.dto.response.GetFollowManageListResponse;
import com.wp.user.domain.follow.entity.FollowManage;
import com.wp.user.domain.follow.repository.FollowManageRepository;
import com.wp.user.domain.user.entity.Auth;
import com.wp.user.domain.user.entity.User;
import com.wp.user.domain.user.repository.UserRepository;
import com.wp.user.global.common.code.ErrorCode;
import com.wp.user.global.common.request.AccessTokenRequest;
import com.wp.user.global.common.request.ExtractionRequest;
import com.wp.user.global.common.service.AuthClient;
import com.wp.user.global.common.service.JwtService;
import com.wp.user.global.exception.BusinessExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowManageServiceImpl implements FollowManageService {
    private final FollowManageRepository followManageRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthClient authClient;

    // 팔로워 목록 조회
    @Override
    public GetFollowManageListResponse getFollowerManages(HttpServletRequest httpServletRequest) {// 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build()).getInfos();
        // 팔로우 목록
        List<FollowManage> followManages = followManageRepository.findAllByFollowingId(Long.valueOf(infos.get("userId")));
        return GetFollowManageListResponse.fromFollower(followManages);
    }

    // 팔로잉 목록 조회
    @Override
    public GetFollowManageListResponse getFollowingManages(HttpServletRequest httpServletRequest) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build()).getInfos();
        // 팔로우 목록
        List<FollowManage> followManages = followManageRepository.findAllByFollowerId(Long.valueOf(infos.get("userId")));
        return GetFollowManageListResponse.fromFollowing(followManages);
    }

    // 팔로우 여부 조회
    @Override
    public FollowStatusResponse getFollowStatus(HttpServletRequest httpServletRequest, Long sellerId) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build()).getInfos();
        // 팔로우 여부
        boolean followStatus = followManageRepository.existsByFollowerIdAndFollowingId(Long.valueOf(infos.get("userId")), sellerId);
        return FollowStatusResponse.builder().isFollow(followStatus).build();
    }

    // 팔로우 등록
    @Override
    @Transactional
    public void addFollow(HttpServletRequest httpServletRequest, AddFollowManageRequest addFollowManageRequest) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build()).getInfos();
        // 팔로워
        User follower = userRepository.findById(Long.valueOf(infos.get("userId"))).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_ID));
        // 팔로잉
        User following = userRepository.findById(addFollowManageRequest.getSellerId()).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER_ID));
        try {
            if(!following.getAuth().equals(Auth.SELLER)) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
        }
        FollowManage followManage = FollowManage.builder()
                .follower(follower)
                .following(following)
                .followAlarmSetting(addFollowManageRequest.isAlarmSetting()).build();
        followManageRepository.save(followManage);
    }

    // 팔로우 취소
    @Override
    @Transactional
    public void removeFollow(HttpServletRequest httpServletRequest, Long sellerId) {
        // 헤더 Access Token 추출
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build()).getInfos();
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER_ID));
        try {
            if(!seller.getAuth().equals(Auth.SELLER)) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
        }
        followManageRepository.deleteByFollowerIdAndFollowingId(Long.valueOf(infos.get("userId")), sellerId);
    }
}
