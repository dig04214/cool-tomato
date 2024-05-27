package com.wp.user.domain.seller.repository.search;

import com.wp.user.domain.seller.dto.response.GetSellerResponse;

import java.util.Optional;

public interface SellerSearchRepository {
    Optional<GetSellerResponse> findSellerByUserId(Long userId);
}
