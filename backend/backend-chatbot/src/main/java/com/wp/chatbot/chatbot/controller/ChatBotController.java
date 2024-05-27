package com.wp.chatbot.chatbot.controller;

import com.wp.chatbot.chatbot.dto.request.ChatbotCreateRequest;
import com.wp.chatbot.chatbot.dto.request.ChatbotSearchRequest;
import com.wp.chatbot.chatbot.dto.request.ChatbotUpdateRequest;
import com.wp.chatbot.chatbot.service.ChatbotService;
import com.wp.chatbot.global.common.code.ErrorCode;
import com.wp.chatbot.global.common.code.SuccessCode;
import com.wp.chatbot.global.common.request.AccessTokenRequest;
import com.wp.chatbot.global.common.request.ExtractionRequest;
import com.wp.chatbot.global.common.response.ErrorResponse;
import com.wp.chatbot.global.common.response.SuccessResponse;
import com.wp.chatbot.global.common.service.AuthClient;
import com.wp.chatbot.global.common.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chatbots")
@Tag(name="챗봇 API",description = "챗봇 관리용 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatBotController {

    private final ChatbotService chatbotService;
    private final JwtService jwtService;
    private final AuthClient authClient;

    @GetMapping("/list")
    @Operation(summary = "판매자가 보는 챗봇 질의응답 목록",description = "판매자가 자신이 등록한 챗봇 질의응답 목록 확인")
    public ResponseEntity<?> getChatbotList(HttpServletRequest httpServletRequest,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size){

        //판매자 권한이 있는지 확인
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 헤더 Access Token 추출
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 판매자일 경우만 저장
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId", "auth")).build()).getInfos();

        if(!infos.get("auth").equals("SELLER")) {
            ErrorResponse response = ErrorResponse.of(ErrorCode.FORBIDDEN_ERROR, ErrorCode.FORBIDDEN_ERROR.getMessage());
            return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        }

        Long userId = Long.valueOf(infos.get("userId"));
        ChatbotSearchRequest request = ChatbotSearchRequest.builder()
                                                        .page(page)
                                                        .size(size)
                                                        .sellerId(userId).build();

        Map<String,Object> result = chatbotService.search(request);

        SuccessResponse response = SuccessResponse.builder()
                .data(result)
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage()).build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "챗봇 질의응답 등록",description = "판매자가 챗봇 질의응답을 등록함")
    public ResponseEntity<?> createChatbot(HttpServletRequest httpServletRequest,
                                           @RequestBody @Valid ChatbotCreateRequest request){
        //판매자 권한이 있는지 확인
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 헤더 Access Token 추출
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 판매자
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId", "auth")).build()).getInfos();

        if(!infos.get("auth").equals("SELLER")) {
            ErrorResponse response = ErrorResponse.of(ErrorCode.FORBIDDEN_ERROR, ErrorCode.FORBIDDEN_ERROR.getMessage());
            return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        }

        Long userId = Long.valueOf(infos.get("userId"));
        chatbotService.save(request,userId);

        SuccessResponse response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "챗봇 질의응답 수정",description = "판매자가 챗봇 질의응답을 수정함")
    public ResponseEntity<?> updateChatbot(HttpServletRequest httpServletRequest,
                                           @RequestBody @Valid ChatbotUpdateRequest request){
        //판매자 권한이 있는지 확인
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 헤더 Access Token 추출
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 판매자
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId", "auth")).build()).getInfos();

        if(!infos.get("auth").equals("SELLER")) {
            ErrorResponse response = ErrorResponse.of(ErrorCode.FORBIDDEN_ERROR, ErrorCode.FORBIDDEN_ERROR.getMessage());
            return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        }

        Long userId = Long.valueOf(infos.get("userId"));

        chatbotService.update(request,userId);

        SuccessResponse response = SuccessResponse.builder()
                .status(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage()).build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{chatbotId}")
    @Operation(summary = "챗봇 질의응답 삭제",description = "판매자가 챗봇 질의응답을 삭제함")
    public ResponseEntity<?> deleteChatbot(HttpServletRequest httpServletRequest,
                                           @PathVariable Long chatbotId){
        //판매자 권한이 있는지 확인
        String accessToken = jwtService.resolveAccessToken(httpServletRequest);
        // 헤더 Access Token 추출
        authClient.validateToken(AccessTokenRequest.builder().accessToken(accessToken).build());
        // 권한이 판매자
        Map<String, String> infos = authClient.extraction(ExtractionRequest.builder().accessToken(accessToken).infos(List.of("userId", "auth")).build()).getInfos();

        if(!infos.get("auth").equals("SELLER")) {
            ErrorResponse response = ErrorResponse.of(ErrorCode.FORBIDDEN_ERROR, ErrorCode.FORBIDDEN_ERROR.getMessage());
            return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        }

        Long userId = Long.valueOf(infos.get("userId"));

        chatbotService.delete(chatbotId,userId);

        SuccessResponse response = SuccessResponse.builder()
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
