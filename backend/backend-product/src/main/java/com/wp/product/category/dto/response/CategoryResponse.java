package com.wp.product.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "카테고리 조회를 위한 응답 객체")
public class CategoryResponse {
    private Long categoryId;
    private Long categoryMasterId;
    private String categoryCode;
    private String categoryContent;
    private int seq;
}