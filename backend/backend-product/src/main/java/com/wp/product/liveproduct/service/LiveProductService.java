package com.wp.product.liveproduct.service;

import com.wp.product.liveproduct.dto.request.LiveProductCreateRequest;
import com.wp.product.liveproduct.dto.request.LiveProductSearchRequest;
import com.wp.product.liveproduct.dto.response.LiveBroadcastProductResponse;

import java.util.List;
import java.util.Map;

public interface LiveProductService {
    List<LiveBroadcastProductResponse> findLiveBroadcastProduct();

    Map<String, Object> findLiveProduct(LiveProductSearchRequest request);

    void saveLiveProduct(List<LiveProductCreateRequest> liveProductRequestList);

    void deleteLiveProduct(Long liveId);
}
