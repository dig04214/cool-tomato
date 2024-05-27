package com.wp.analyze.domain.postmortem.controller;

import com.wp.analyze.domain.postmortem.service.PostmortemService;
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
@RequestMapping(value = "/v1/analyze/postmortem")
@Tag(name = "postmortem", description = "분석 API Doc")
public class PostmortemController {

    private final PostmortemService postmortemService;

    @ResponseBody
    @GetMapping("/{broadcastId}")
    @Operation(summary = "사후분석 데이터 반환", description = "사후분석 데이터를 반환합니다.")
    public ResponseEntity<SuccessResponse<String>> getPostmortemInfo(@PathVariable Long broadcastId){
        String result = postmortemService.getPostmortemInfo(broadcastId);
        return new ResponseEntity<>(SuccessResponse.<String>builder().data(result).status(200).message("사후분석 데이터 반환을 성공했습니다.").build(), HttpStatus.OK);
    }
}
