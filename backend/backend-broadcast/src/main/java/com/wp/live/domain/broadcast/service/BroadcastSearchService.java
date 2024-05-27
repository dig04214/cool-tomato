package com.wp.live.domain.broadcast.service;

import com.wp.live.domain.broadcast.dto.controller.response.SearchByDateResponseDto;
import com.wp.live.domain.broadcast.dto.controller.response.SearchBySellerResponse;
import com.wp.live.domain.broadcast.dto.controller.response.SearchByTitleResponseDto;
import com.wp.live.domain.broadcast.dto.controller.response.GetCarouselResponseDto;

public interface BroadcastSearchService {
    public GetCarouselResponseDto getCarousel();
    public SearchByTitleResponseDto searchLivebBroadcastTitle(String keyword, int page, int size);
    public SearchBySellerResponse searchLivebBroadcastSeller(String sellerKeyword, int page, int size);
    public SearchByDateResponseDto searchLiveBroadcastDate(String statDate, int page, int size);

}
