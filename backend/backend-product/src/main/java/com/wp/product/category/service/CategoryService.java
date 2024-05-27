package com.wp.product.category.service;

import com.wp.product.category.dto.response.CategoryMasterResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryMasterResponse> findAll();
}