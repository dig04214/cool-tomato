package com.wp.live.domain.broadcast.controller;

import com.wp.live.domain.broadcast.dto.controller.request.*;
import com.wp.live.domain.broadcast.dto.controller.response.*;
import com.wp.live.domain.broadcast.service.BroadcastService;
import com.wp.live.domain.broadcast.service.AuthService;
import com.wp.live.global.common.code.ErrorCode;
import com.wp.live.global.common.response.SuccessResponse;
import com.wp.live.global.exception.BusinessExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/live/broadcast")
@Tag(name = "live", description = "라이브 API Doc")
public class BroadcastController {

    private final AuthService authService;
    private final BroadcastService broadcastService;

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @ResponseBody
    @PostMapping("/reservation")
    @Operation(summary = "라이브 예약", description = "라이브를 예약합니다.")
    public ResponseEntity<SuccessResponse<ReservationResponseDto>> reserveBroadcast(@RequestBody @Validated ReservationRequestDto reservationRequestDto){
        String accessToken = reservationRequestDto.getAccessToken();
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                Long userId = authService.getUserId(accessToken);
                Long broadcastId = broadcastService.reserveBroadcast(reservationRequestDto, userId);
                ReservationResponseDto result = ReservationResponseDto.builder().liveBroadcastId(broadcastId).build();
                return new ResponseEntity<>(SuccessResponse.<ReservationResponseDto>builder().data(result).status(201).message("방송 예약 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/start")
    @Operation(summary = "라이브 시작", description = "라이브를 시작합니다.")
    public ResponseEntity<SuccessResponse<StartResponseDto>> startBroadcast(@RequestBody @Validated StartRequestDto startRequestDto){
        String accessToken = startRequestDto.getAccessToken();
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                Long userId = authService.getUserId(accessToken);
                Map<String, String> tokenAndtopic = broadcastService.startBroadcast(startRequestDto, userId);
                StartResponseDto result = StartResponseDto.builder().token(tokenAndtopic.get("token")).topicId(tokenAndtopic.get("topicId")).build();
                return new ResponseEntity<>(SuccessResponse.<StartResponseDto>builder().data(result).status(201).message("방송 시작 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }

    @ResponseBody
    @PostMapping("/participation")
    @Operation(summary = "라이브 참여", description = "라이브에 참여합니다.")
    public ResponseEntity<SuccessResponse<ParticipationResponseDto>> participateBroadcast(@RequestBody @Validated ParticipationRequestDto participationRequestDto){
        String token = broadcastService.participateBroadcast(participationRequestDto);
        ParticipationResponseDto result = ParticipationResponseDto.builder().token(token).build();
        return new ResponseEntity<>(SuccessResponse.<ParticipationResponseDto>builder().data(result).status(201).message("방송 참여 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/stop")
    @Operation(summary = "라이브 종료", description = "라이브를 종료합니다.")
    public ResponseEntity<SuccessResponse<Boolean>> stopBroadcast(@RequestBody @Validated StopRequestDto stopRequestDto){
        String accessToken = stopRequestDto.getAccessToken();
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                Long userId = authService.getUserId(accessToken);
                broadcastService.stopBroadcast(stopRequestDto, userId);
                return new ResponseEntity<>(SuccessResponse.<Boolean>builder().data(true).status(200).message("방송 종료 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/stop")
    @Operation(summary = "종료된 방송 목록", description = "종료된 방송 목록을 반환합니다.")
    public ResponseEntity<SuccessResponse<GetBroadcastListResponseDto>> getStopBroadcastList(@RequestParam int page, @RequestParam int size, HttpServletRequest httpServletRequest){
        String accessToken = authService.resolveAccessToken(httpServletRequest);
        if(accessToken == null)throw new BusinessExceptionHandler("토큰 형식이 잘못되었습니다.", ErrorCode.BAD_REQUEST_ERROR);
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                Long userId = authService.getUserId(accessToken);
                GetBroadcastListResponseDto result = broadcastService.getStopBroadcastListById(userId, page, size);
                return new ResponseEntity<>(SuccessResponse.<GetBroadcastListResponseDto>builder().data(result).status(200).message("종료된 방송 목록 반환 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/list")
    @Operation(summary = "방송 목록 검색", description = "방송 목록 반환합니다.")
    public ResponseEntity<SuccessResponse<GetBroadcastListResponseDto>> getBroadcastList(@RequestParam int page, @RequestParam int size){
        GetBroadcastListResponseDto result = broadcastService.getBroadcastList(page, size);
        return new ResponseEntity<>(SuccessResponse.<GetBroadcastListResponseDto>builder().data(result).status(200).message("방송 리스트 반환 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/reservation")
    @Operation(summary = "예약 방송 설정 변경", description = "예약 방송 설정을 변경합니다.")
    public ResponseEntity<SuccessResponse<Boolean>> updateReservationBroadcast(@RequestBody @Validated UpdateReservationRequestDto updateReservationRequestDto){
        String accessToken = updateReservationRequestDto.getAccessToken();
        System.out.println(accessToken);
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                broadcastService.updateBroadcastInfo(updateReservationRequestDto);
                return new ResponseEntity<>(SuccessResponse.<Boolean>builder().data(true).status(201).message("방송 예약 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/info")
    @Operation(summary = "방송 상세", description = "방송의 상세 정보를 반환합니다.")
    public ResponseEntity<SuccessResponse<GetBroadcastInfoResponseDto>> getBroadcastInfo(@RequestParam Long broadcastId, HttpServletRequest httpServletRequest){
        String accessToken = authService.resolveAccessToken(httpServletRequest);
        if(accessToken == null)throw new BusinessExceptionHandler("토큰 형식이 잘못되었습니다.", ErrorCode.BAD_REQUEST_ERROR);
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                GetBroadcastInfoResponseDto result = broadcastService.getBroadcastInfo(broadcastId);
                return new ResponseEntity<>(SuccessResponse.<GetBroadcastInfoResponseDto>builder().data(result).status(201).message("방송 상세 반환 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/reservation")
    @Operation(summary = "예약 방송 목록", description = "예약 방송 목록 반환합니다.")
    public ResponseEntity<SuccessResponse<GetBroadcastListResponseDto>> getReservationBroadcastList(@RequestParam int page, @RequestParam int size, HttpServletRequest httpServletRequest){
        String accessToken = authService.resolveAccessToken(httpServletRequest);
        if(accessToken == null)throw new BusinessExceptionHandler("토큰 형식이 잘못되었습니다.", ErrorCode.BAD_REQUEST_ERROR);
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                Long userId = authService.getUserId(accessToken);
                GetBroadcastListResponseDto result = broadcastService.getReservationBroadcastListById(userId, page, size);
                return new ResponseEntity<>(SuccessResponse.<GetBroadcastListResponseDto>builder().data(result).status(200).message("종료된 방송 목록 반환 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }

    @ResponseBody
    @DeleteMapping("/reservation")
    @Operation(summary = "예약 방송 삭제", description = "예약된 방송을 삭제 합니다.")
    public ResponseEntity<SuccessResponse<Boolean>> deleteReservationBroadcast(@RequestParam Long broadcastId, HttpServletRequest httpServletRequest){
        String accessToken = authService.resolveAccessToken(httpServletRequest);
        if(accessToken == null)throw new BusinessExceptionHandler("토큰 형식이 잘못되었습니다.", ErrorCode.BAD_REQUEST_ERROR);
        if(authService.validateToken(accessToken)){
            if(authService.getAuth(accessToken).equals("SELLER")){
                Long userId = authService.getUserId(accessToken);
                broadcastService.deleteBroadcast(broadcastId, userId);
                return new ResponseEntity<>(SuccessResponse.<Boolean>builder().data(true).status(200).message("예약 방송 삭제 성공").build(), HttpStatus.OK);
            }else{
                throw new BusinessExceptionHandler("판매자만 가능한 기능입니다.", ErrorCode.FORBIDDEN_ERROR);
            }
        }else{
            throw new BusinessExceptionHandler("만료된 토큰입니다.", ErrorCode.FORBIDDEN_ERROR);
        }
    }
}
