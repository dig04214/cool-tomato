package com.wp.user.domain.seller.controller;

import com.wp.user.domain.seller.dto.request.AddSellerInfoRequest;
import com.wp.user.domain.seller.dto.response.GetSellerInfoListResponse;
import com.wp.user.domain.seller.dto.response.GetSellerInfoResponse;
import com.wp.user.domain.seller.dto.response.GetSellerResponse;
import com.wp.user.domain.seller.service.SellerInfoService;
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
@RequestMapping("v1/users/sellers")
@Tag(name = "판매자 API", description = "판매자 전환 용 API")
public class SellerInfoController {

    private final SellerInfoService sellerInfoService;

    @GetMapping("/{seller-id}")
    @Operation(summary = "판매자 상세 정보 조회", description = "사용자는 판매자 상세 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getSeller(@NotNull(message = "판매자 회원 ID를 입력해 주세요.") @PathVariable(name = "seller-id") Long sellerId) {
        GetSellerResponse getSellerResponse = sellerInfoService.getSeller(sellerId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getSellerResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin")
    @Operation(summary = "판매자 전환 신청 목록 조회", description = "관리자는 판매자 전환 신청 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getSellerInfos(HttpServletRequest httpServletRequest, @NotNull(message = "페이지 번호를 입력해 주세요.") @RequestParam int page, @NotNull(message = "크기를 입력해 주세요.") @RequestParam int size) {
        GetSellerInfoListResponse getSellerInfoListResponse = sellerInfoService.getSellerInfos(httpServletRequest, page, size);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getSellerInfoListResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin-sellers/{seller-info-id}")
    @Operation(summary = "판매자 전환 신청 상세 조회", description = "관리자와 구매자는 판매자 전환 신청을 상세 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getSellerInfo(HttpServletRequest httpServletRequest, @NotNull(message = "판매자 정보 ID를 입력해 주세요.") @PathVariable(name = "seller-info-id") Long sellerInfoId) {
        GetSellerInfoResponse getSellerInfoResponse = sellerInfoService.getSellerInfo(httpServletRequest, sellerInfoId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getSellerInfoResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "판매자 전환 신청", description = "사용자는 판매자 정보를 입력하여 전환 신청합니다.")
    public ResponseEntity<SuccessResponse<?>> addSellerInfo(HttpServletRequest httpServletRequest, @Valid @RequestBody AddSellerInfoRequest addSellerInfoRequest) {
        sellerInfoService.addSellerInfo(httpServletRequest, addSellerInfoRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/approve/{seller-info-id}")
    @Operation(summary = "판매자 전환 승인", description = "관리자는 판매자 정보 id로 판매자 전환 신청을 승인합니다.")
    public ResponseEntity<SuccessResponse<?>> approveSeller(HttpServletRequest httpServletRequest, @NotNull(message = "판매자 정보 ID를 입력해 주세요.") @PathVariable(name = "seller-info-id") Long sellerInfoId) {
        sellerInfoService.modifySellerStatusTrue(httpServletRequest, sellerInfoId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/cancel/{seller-info-id}")
    @Operation(summary = "판매자 전환 철회", description = "관리자는 판매자 정보 id로 판매자 전환을 철회합니다.")
    public ResponseEntity<SuccessResponse<?>> cancelSeller(HttpServletRequest httpServletRequest, @NotNull(message = "판매자 정보 ID를 입력해 주세요.") @PathVariable(name = "seller-info-id") Long sellerInfoId) {
        sellerInfoService.modifySellerStatusFalse(httpServletRequest, sellerInfoId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
