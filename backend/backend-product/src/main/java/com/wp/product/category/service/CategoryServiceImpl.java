package com.wp.product.category.service;

import com.wp.product.category.dto.response.CategoryMasterResponse;
import com.wp.product.category.entity.CategoryMaster;
import com.wp.product.category.repository.CategoryMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryMasterRepository categoryMasterRepository;

    @Override
    public List<CategoryMasterResponse> findAll() {
        //카테고리 마스터 리스트(seq로 정렬)
        List<CategoryMaster> categoryMasterList = categoryMasterRepository.findAll(Sort.by("seq"));
        List<CategoryMasterResponse> responses = new ArrayList<>();

        for (CategoryMaster categoryMaster : categoryMasterList){
            responses.add(CategoryMasterResponse.builder()
                    .categoryMasterId(categoryMaster.getCategoryMasterId())
                    .categoryMasterCode(categoryMaster.getCategoryMasterCode())
                    .categoryMasterContent(categoryMaster.getCategoryMasterContent())
                    .categoryList(categoryMaster.getCategory()).build());
        }

        return responses;
    }
}