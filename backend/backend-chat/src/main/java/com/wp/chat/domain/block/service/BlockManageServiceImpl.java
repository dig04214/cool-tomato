package com.wp.chat.domain.block.service;

import com.wp.chat.domain.block.dto.response.GetBlockManageListResponse;
import com.wp.chat.domain.block.entity.BlockManage;
import com.wp.chat.domain.block.repository.BlockManageRepository;
import com.wp.chat.global.common.code.ErrorCode;
import com.wp.chat.global.common.entity.Auth;
import com.wp.chat.global.common.entity.User;
import com.wp.chat.global.common.repository.UserRepository;
import com.wp.chat.global.common.request.AccessTokenRequest;
import com.wp.chat.global.common.request.ExtractionRequest;
import com.wp.chat.global.common.service.AuthClient;
import com.wp.chat.global.common.service.JwtService;
import com.wp.chat.global.exception.BusinessExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlockManageServiceImpl implements  BlockManageService {

    private final BlockManageRepository blockManageRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthClient authClient;
    private final String BLOCK_LIST = "block_list";

    // 차단 목록 조회
    @Override
    public GetBlockManageListResponse getBlockManages(HttpServletRequest httpServletRequest) {
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId", "auth")).build()).getInfos();
        try {
            if(!infos.get("auth").equals("SELLER")) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
        }
        List<BlockManage> blockManages = blockManageRepository.findAllBySellerId(Long.valueOf(infos.get("userId")));
        return GetBlockManageListResponse.from(blockManages);
    }

    // 차단 목록 조회 (sellerId)

//    @Cacheable(cacheNames = BLOCK_LIST, key = "#sellerId", condition = "#sellerId != null", cacheManager = "cacheManager")
    @Override
    public List<Long> getBlockManagesBySellerId(Long sellerId) {
        return blockManageRepository.findAllBlockedIdBySellerId(sellerId);
    }

    // 차단 등록
    @Override
    @Transactional
//    @CacheEvict(cacheNames = BLOCK_LIST, key = "#sellerId", cacheManager = "cacheManager")
    public void addBlocked(Long sellerId, Long blockedId) {
        // 판매자
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_SELLER_ID));
        try {
            if(!seller.getAuth().equals(Auth.SELLER)) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
        }
        User blocked = userRepository.findById(blockedId).orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_FOUND_USER_ID));
        blockManageRepository.save(BlockManage.builder().seller(seller).blocked(blocked).build());
    }

    // 차단 삭제
    @Override
    @Transactional
//    @CacheEvict(cacheNames = BLOCK_LIST, key = "#sellerId", cacheManager = "cacheManager")
    public void removeBlocked(String auth, Long sellerId, Long blockedId) {
        try {
            if(!auth.equals("SELLER")) {
                throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
            }
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.NOT_SELLER);
        }

        blockManageRepository.deleteByBlockedIdAndSellerId(blockedId, sellerId);
    }
}
