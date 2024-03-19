package com.wp.product.liveproduct.repository.search;

import com.wp.product.liveproduct.dto.request.LiveProductSearchRequest;
import com.wp.product.liveproduct.dto.response.LiveBroadcastProductResponse;
import com.wp.product.liveproduct.dto.response.LiveProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import java.util.List;

public interface LiveProductSearch {
    @Transactional
    Page<LiveProductResponse> search(LiveProductSearchRequest request);

    @Transactional
    List<LiveBroadcastProductResponse> searchLiveBroadcastProduct();
}
