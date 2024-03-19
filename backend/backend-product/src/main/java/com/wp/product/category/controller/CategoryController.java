package com.wp.product.category.controller;

import com.wp.product.category.dto.response.CategoryMasterResponse;
import com.wp.product.category.service.CategoryService;
import com.wp.product.global.common.code.SuccessCode;
import com.wp.product.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
@Tag(name="카테고리 API",description = "카테고리 조회용 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping
    @Operation(summary = "카테고리 조회",description = "카테고리 리스트를 조회함")
    public ResponseEntity<?> categoryList(){
        //모든 카테고리 조회함
        List<CategoryMasterResponse> categoryResponse = categoryService.findAll();

        SuccessResponse response = SuccessResponse.builder()
                                    .data(categoryResponse)
                                    .status(SuccessCode.SELECT_SUCCESS.getStatus())
                                    .message(SuccessCode.SELECT_SUCCESS.getMessage()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}