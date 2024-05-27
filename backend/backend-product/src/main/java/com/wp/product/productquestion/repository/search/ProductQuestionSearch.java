package com.wp.product.productquestion.repository.search;

import com.wp.product.productquestion.dto.request.ProductQuestionSearchRequest;
import com.wp.product.productquestion.dto.response.ProductQuestionResponse;
import com.wp.product.productquestion.dto.response.ProductQuestionSearchResponse;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductQuestionSearch {
    Page<ProductQuestionSearchResponse> search(ProductQuestionSearchRequest request);

    Optional<ProductQuestionResponse> findProductQuestion(Long productQuestionId);

    Page<ProductQuestionMyListResponse> getMyProductQuestionList(ProductQuestionSearchRequest request);

    Page<ProductQuestionMyListResponse> getMyQuestionList(ProductQuestionSearchRequest request);
}
