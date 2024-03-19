package com.wp.chat.domain.block.controller;

import com.wp.chat.domain.block.dto.response.GetBlockManageListResponse;
import com.wp.chat.domain.block.service.BlockManageService;
import com.wp.chat.global.common.code.SuccessCode;
import com.wp.chat.global.common.request.AccessTokenRequest;
import com.wp.chat.global.common.request.ExtractionRequest;
import com.wp.chat.global.common.response.SuccessResponse;
import com.wp.chat.global.common.service.AuthClient;
import com.wp.chat.global.common.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/chat/block")
@Tag(name = "차단 API", description = "차단 관리 용 API")
public class BlockManageController {
    private final BlockManageService blockManageService;
    private final AuthClient authClient;
    private final JwtService jwtService;

    @GetMapping
    @Operation(summary = "차단 목록 조회", description = "판매자의 차단 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getBlockManages(HttpServletRequest httpServletRequest) {
        GetBlockManageListResponse getBlockManageListResponse = blockManageService.getBlockManages(httpServletRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getBlockManageListResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{blocked-id}")
    @Operation(summary = "차단 등록", description = "판매자는 구매자 ID로 구매자를 차단 합니다.")
    public ResponseEntity<SuccessResponse<?>> addBlocked(HttpServletRequest httpServletRequest, @NotNull(message = "차단할 회원 ID를 입력해 주세요.") @PathVariable(name = "blocked-id") Long blockedId) {
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId")).build()).getInfos();
        blockManageService.addBlocked(Long.valueOf(infos.get("userId")), blockedId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{blocked-id}")
    @Operation(summary = "차단 삭제", description = "판매자는 구매자 ID로 구매자 차단을 취소합니다.")
    public ResponseEntity<SuccessResponse<?>> removeBlocked(HttpServletRequest httpServletRequest, @NotNull(message = "차단할 회원 ID를 입력해 주세요.") @PathVariable(name = "blocked-id") Long blockedId) {
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 인증
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 회원 정보 추출
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId", "auth")).build()).getInfos();
        blockManageService.removeBlocked(infos.get("auth"), Long.valueOf(infos.get("userId")), blockedId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
