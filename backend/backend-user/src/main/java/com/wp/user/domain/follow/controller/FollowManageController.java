package com.wp.user.domain.follow.controller;

import com.wp.user.domain.follow.dto.request.AddFollowManageRequest;
import com.wp.user.domain.follow.dto.response.FollowStatusResponse;
import com.wp.user.domain.follow.dto.response.GetFollowManageListResponse;
import com.wp.user.domain.follow.service.FollowManageService;
import com.wp.user.global.common.code.SuccessCode;
import com.wp.user.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users/follow")
@Tag(name = "팔로우 API", description = "팔로우 관리 용 API")
public class FollowManageController {

    private final FollowManageService followManageService;

    @GetMapping("/follower")
    @Operation(summary = "팔로워 목록 조회", description = "판매자의 팔로워 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getFollowerManages(HttpServletRequest httpServletRequest) {
        GetFollowManageListResponse getFollowManageListResponse = followManageService.getFollowerManages(httpServletRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getFollowManageListResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/following")
    @Operation(summary = "팔로잉 목록 조회", description = "구매자의 팔로잉 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getFollowingManages(HttpServletRequest httpServletRequest) {
        GetFollowManageListResponse getFollowManageListResponse = followManageService.getFollowingManages(httpServletRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getFollowManageListResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{seller-id}")
    @Operation(summary = "팔로우 여부 조회", description = "사용자가 해당 판매자 팔로우 여부를 확인합니다.")
    public ResponseEntity<SuccessResponse<?>> getFollowStatus(HttpServletRequest httpServletRequest, @NotNull(message = "판매자 ID를 입력해 주세요.") @PathVariable(name = "seller-id") Long sellerId) {
        FollowStatusResponse followStatusResponse = followManageService.getFollowStatus(httpServletRequest, sellerId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(followStatusResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "팔로우 등록", description = "사용자는 구매자 ID로 구매자를 팔로우 합니다.")
    public ResponseEntity<SuccessResponse<?>> addFollow(HttpServletRequest httpServletRequest, @Valid @RequestBody AddFollowManageRequest addFollowManageRequest) {
        followManageService.addFollow(httpServletRequest, addFollowManageRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{seller-id}")
    @Operation(summary = "팔로우 삭제", description = "사용자는 구매자 ID로 구매자를 팔로우 취소합니다.")
    public ResponseEntity<SuccessResponse<?>> removeFollow(HttpServletRequest httpServletRequest, @NotNull(message = "판매자 ID를 입력해 주세요.") @PathVariable(name = "seller-id") Long sellerId) {
        followManageService.removeFollow(httpServletRequest, sellerId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
