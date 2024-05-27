package com.wp.analyze.domain.realtime.controller;

import com.wp.analyze.domain.realtime.service.RealtimeAnalyzeService;
import com.wp.analyze.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/analyze/realtime")
@Tag(name = "realtime", description = "분석 API Doc")
public class RealtimeController {

    private final RealtimeAnalyzeService realtimeAnalyzeService;
    @ResponseBody
    @GetMapping("/keyword/{roomId}")
    @Operation(summary = "실시간 핫 키워드 반환", description = "실시간 채팅 핫 키워드를 반환합니다.")
    public ResponseEntity<SuccessResponse<List<String>>> getHotKeywords(@PathVariable String roomId){
        List<String> result = realtimeAnalyzeService.getHotKeywords(roomId);
        return new ResponseEntity<>(SuccessResponse.<List<String>>builder().data(result).status(200).message("실시간 채팅 핫 키워드를 반환을 성공했습니다.").build(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/connection/{roomId}")
    @Operation(summary = "접속자 수 반환", description = "방송 접속자 수를 반환합니다.")
    public ResponseEntity<SuccessResponse<Long>> getConnectionNum(@PathVariable String roomId){
        Long result = realtimeAnalyzeService.getConnectionNum(roomId);
        return new ResponseEntity<>(SuccessResponse.<Long>builder().data(result).status(200).message("실시간 채팅 핫 키워드를 반환을 성공했습니다.").build(), HttpStatus.OK);
    }
}
