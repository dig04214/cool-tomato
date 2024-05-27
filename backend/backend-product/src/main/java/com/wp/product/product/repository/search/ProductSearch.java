package com.wp.product.product.repository.search;

import com.wp.product.product.dto.request.ProductSearchRequest;
import com.wp.product.product.dto.response.ProductFindResponse;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface ProductSearch {
    Optional<ProductFindResponse> searchByProductId(Long productId);

    Page<ProductFindResponse> search(ProductSearchRequest request);

    Page<ProductFindResponse> searchMyProducts(ProductSearchRequest request);

    Page<ProductFindResponse> searchRecentProducts(List<Long> idList);
}
