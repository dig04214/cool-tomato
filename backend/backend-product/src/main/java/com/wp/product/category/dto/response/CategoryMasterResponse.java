package com.wp.product.category.dto.response;

import com.wp.product.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@Schema(description = "카테고리 마스터 조회를 위한 응답 객체")
public class CategoryMasterResponse {
    private Long categoryMasterId;
    private String categoryMasterCode;
    private String categoryMasterContent;
    private List<Category> categoryList;
}