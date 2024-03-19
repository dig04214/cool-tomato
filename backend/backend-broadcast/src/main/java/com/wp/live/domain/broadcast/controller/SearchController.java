package com.wp.live.domain.broadcast.controller;

import com.wp.live.domain.broadcast.dto.controller.response.SearchByDateResponseDto;
import com.wp.live.domain.broadcast.dto.controller.response.GetCarouselResponseDto;
import com.wp.live.domain.broadcast.dto.controller.response.SearchBySellerResponse;
import com.wp.live.domain.broadcast.dto.controller.response.SearchByTitleResponseDto;
import com.wp.live.domain.broadcast.service.BroadcastSearchService;
import com.wp.live.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/live/search")
@Tag(name = "search", description = "라이브 API Doc")
public class SearchController {

    private final BroadcastSearchService broadcastSearchService;

    @ResponseBody
    @GetMapping("/carousel")
    @Operation(summary = "캐러셀 방송 목록", description = "캐러셀에 삽입될 방송 목록 반환합니다.")
    public ResponseEntity<SuccessResponse<GetCarouselResponseDto>> getCarousel(){
        GetCarouselResponseDto result = broadcastSearchService.getCarousel();
        return new ResponseEntity<>(SuccessResponse.<GetCarouselResponseDto>builder().data(result).status(200).message("캐러셀 데이터 반환 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/seller")
    @Operation(summary = "판매자 기반 방송 목록 검색", description = "판매자 이름을 기반으로 방송 목록 검색합니다.")
    public ResponseEntity<SuccessResponse<SearchBySellerResponse>> searchBySeller(@RequestParam String name, @RequestParam int page, @RequestParam int size){
        System.out.println(name);
        SearchBySellerResponse result = broadcastSearchService.searchLivebBroadcastSeller(name, page, size);
        return new ResponseEntity<>(SuccessResponse.<SearchBySellerResponse>builder().data(result).status(200).message("닉네임 기반 검색 결과 반환 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/title")
    @Operation(summary = "방속 제목 기반 방송 목록 검색", description = "방속 제목을 기반으로 방송 목록 반환합니다.")
    public ResponseEntity<SuccessResponse<SearchByTitleResponseDto>> searchByTitle(@RequestParam String keyword, @RequestParam int page, @RequestParam int size){
        System.out.println(keyword);
        SearchByTitleResponseDto result = broadcastSearchService.searchLivebBroadcastTitle(keyword, page, size);

        return new ResponseEntity<>(SuccessResponse.<SearchByTitleResponseDto>builder().data(result).status(200).message("제목 기반 검색 결과 반환 성공").build(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/date")
    @Operation(summary = "날짜 기반 방송 목록 검색", description = "방속 제목을 기반으로 방송 목록 반환합니다.")
    public ResponseEntity<SuccessResponse<SearchByDateResponseDto>> searchByDate(@RequestParam String date, int page, int size) {
        SearchByDateResponseDto result = broadcastSearchService.searchLiveBroadcastDate(date, page, size);
        return new ResponseEntity<>(SuccessResponse.<SearchByDateResponseDto>builder().data(result).status(200).message("날짜 기반 방송 목록 검색").build(), HttpStatus.OK);
    }
}
