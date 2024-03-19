package com.wp.product.product.service;

import com.wp.product.product.dto.request.ProductCreateRequest;
import com.wp.product.product.dto.request.ProductSearchRequest;
import com.wp.product.product.dto.request.ProductUpdateRequest;
import com.wp.product.product.dto.response.ProductFindResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Map<String, Object> searchProduct(ProductSearchRequest productSearchRequest);

    Map<String, Object> searchMyProducts(ProductSearchRequest productSearchRequest);

    Map<String, Object> searchRecentProducts(List<Long> idList);

    ProductFindResponse findProductById(Long productId);

    void saveProduct(ProductCreateRequest productRequest, Long userId, MultipartFile file);

    void updateProduct(ProductUpdateRequest productReques, Long userId, MultipartFile file);

    void deleteProduct(Long productId, Long userId);

    String saveProductFile(MultipartFile file);
}
