package com.wp.domain.auth.controller;

import com.wp.domain.auth.dto.request.*;
import com.wp.domain.auth.dto.response.ExtractionResponseDto;
import com.wp.domain.auth.dto.response.TokenResponseDto;
import com.wp.domain.auth.service.JwtTokenProviderService;
import com.wp.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
@Tag(name = "Auth", description = "인증/인가  API Doc")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    JwtTokenProviderService jwtTokenProviderService;

    @ResponseBody
    @PostMapping
    @Operation(summary = "토큰 발급", description = "토큰을 발급합니다.")
    public ResponseEntity<SuccessResponse<TokenResponseDto>> createToken(@RequestBody @Validated UserRequestDto user){
        TokenResponseDto tokenResponseDto = jwtTokenProviderService.createToken(user.getUserId(), user.getAuth());
        return new ResponseEntity<>(SuccessResponse.<TokenResponseDto>builder().data(tokenResponseDto).status(201).message("토큰 생성 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping
    @Operation(summary = "토큰 삭제", description = "Refresh 토큰을 삭제합니다.")
    public ResponseEntity<SuccessResponse<Boolean>> deleteToken(@RequestBody @Validated AccessTokenRequestDto accessToken){
        jwtTokenProviderService.validateAccessToken(accessToken.getAccessToken());
        String userId = jwtTokenProviderService.getUserId(accessToken.getAccessToken());
        jwtTokenProviderService.deleteRefreshToken(userId);
        return new ResponseEntity<>(SuccessResponse.<Boolean>builder().data(true).status(200).message("토큰 삭제 성공").build(), HttpStatus.OK);
    }
    @ResponseBody
    @DeleteMapping("/id")
    @Operation(summary = "유저 ID 기반 토큰 삭제", description = "유저 ID를 기반으로 Refresh 토큰을 삭제합니다.")
    public ResponseEntity<SuccessResponse<Boolean>> deleteIdToken(@RequestBody @Validated DeleteIdTokenRequestDto deleteIdTokenRequestDto){
        jwtTokenProviderService.deleteRefreshToken(deleteIdTokenRequestDto.getUserId());
        return new ResponseEntity<>(SuccessResponse.<Boolean>builder().data(true).status(200).message("유저 ID 기반 토큰 삭제 성공").build(), HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping("/validationToken")
    @Operation(summary = "토큰 인증", description = "토큰을 인증합니다.")
    public ResponseEntity<SuccessResponse<Boolean>> validateToken(@RequestBody @Validated AccessTokenRequestDto accessToken){
        jwtTokenProviderService.validateAccessToken(accessToken.getAccessToken());
        return new ResponseEntity<>(SuccessResponse.<Boolean>builder().data(true).status(200).message("토큰 인증 완료").build(), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    public ResponseEntity<SuccessResponse<TokenResponseDto>> reissueToken(@RequestBody @Validated TokenRequestDto tokenRequestDto){
        String userId = jwtTokenProviderService.getUserId(tokenRequestDto.getAccessToken());
        String auth = jwtTokenProviderService.getAuth(tokenRequestDto.getAccessToken());
        jwtTokenProviderService.validateRefreshToken(tokenRequestDto.getRefreshToken(), userId);
        jwtTokenProviderService.deleteRefreshToken(userId);
        TokenResponseDto token = jwtTokenProviderService.createToken(userId, auth);
        return new ResponseEntity<>(SuccessResponse.<TokenResponseDto>builder().data(token).status(201).message("토큰 재발급 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/extraction")
    @Operation(summary = "토큰 정보 추출", description = "토큰에서 정보를 추출합니다.")
    public ResponseEntity<SuccessResponse<ExtractionResponseDto>> extractToken(@RequestBody @Validated ExtractionRequestDto extractionRequestDto){
        jwtTokenProviderService.validateAccessToken(extractionRequestDto.getAccessToken());
        Map<String, String> infos = jwtTokenProviderService.getInfo(extractionRequestDto.getAccessToken(), extractionRequestDto.getInfos());
        ExtractionResponseDto extractionResponseDto = ExtractionResponseDto.builder().infos(infos).build();
        return new ResponseEntity<>(SuccessResponse.<ExtractionResponseDto>builder().data(extractionResponseDto).status(200).message("토큰 정보 추출 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/reissue/auth")
    @Operation(summary = "토큰 권한 변환 후 재발급", description = "토큰을 권한 변환 후 재발급합니다.")
    public ResponseEntity<SuccessResponse<TokenResponseDto>> reissueAuthToken(@RequestBody @Validated ReissueAuthRequestDto reissueAuthRequestDto){
        String userId = jwtTokenProviderService.getUserId(reissueAuthRequestDto.getAccessToken());
        jwtTokenProviderService.validateRefreshToken(reissueAuthRequestDto.getRefreshToken(), userId);
        jwtTokenProviderService.deleteRefreshToken(userId);
        TokenResponseDto token = jwtTokenProviderService.createToken(userId, reissueAuthRequestDto.getAuth());
        return new ResponseEntity<>(SuccessResponse.<TokenResponseDto>builder().data(token).status(201).message("토큰 권환 변경 후 재발급 성공").build(), HttpStatus.OK);
    }

}
