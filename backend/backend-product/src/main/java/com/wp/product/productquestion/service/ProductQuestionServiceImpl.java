package com.wp.product.productquestion.service;

import com.wp.product.global.common.code.ErrorCode;
import com.wp.product.global.exception.BusinessExceptionHandler;
import com.wp.product.product.entity.Product;
import com.wp.product.productquestion.dto.request.ProductQuestionCreateRequest;
import com.wp.product.productquestion.dto.request.ProductQuestionSearchRequest;
import com.wp.product.productquestion.dto.request.ProductQuestionUpdateRequest;
import com.wp.product.productquestion.dto.response.ProductQuestionResponse;
import com.wp.product.productquestion.dto.response.ProductQuestionSearchResponse;
import com.wp.product.productquestion.entity.ProductQuestion;
import com.wp.product.productquestion.repository.ProductQuestionRepository;
import com.wp.product.productquestion.repository.search.ProductQuestionMyListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductQuestionServiceImpl implements ProductQuestionService{

    private final ProductQuestionRepository productQuestionRepository;

    @Override
    public Map<String, Object> getProductQuestionList(ProductQuestionSearchRequest productQuestionSearchRequest) {
        try {
            Page<ProductQuestionSearchResponse> result = productQuestionRepository.search(productQuestionSearchRequest);
            Map<String,Object> map = new HashMap<>();

            map.put("list",result.getContent());
            map.put("totalCount",result.getTotalElements());
            return map;
        }catch (Exception e){
            log.debug(e.getMessage());
            throw new BusinessExceptionHandler("상품 문의 조회 중 에러 발생",ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Map<String, Object> getMyProductQuestionList(ProductQuestionSearchRequest productQuestionSearchRequest) {
        try {
            Page<ProductQuestionMyListResponse> result = productQuestionRepository.getMyProductQuestionList(productQuestionSearchRequest);
            Map<String,Object> map = new HashMap<>();

            map.put("list",result.getContent());
            map.put("totalCount",result.getTotalElements());
            return map;
        }catch (Exception e){
            throw new BusinessExceptionHandler("판매자 상품 문의 조회 중 에러 발생",ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Map<String, Object> getMyQuestionList(ProductQuestionSearchRequest productQuestionSearchRequest) {
        try {
            Page<ProductQuestionMyListResponse> result = productQuestionRepository.getMyQuestionList(productQuestionSearchRequest);
            Map<String,Object> map = new HashMap<>();

            map.put("list",result.getContent());
            map.put("totalCount",result.getTotalElements());
            return map;
        }catch (Exception e){
            throw new BusinessExceptionHandler("구매자 상품 문의 조회 중 에러 발생",ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProductQuestionResponse findProductQuestion(Long productQuestionId) {
        Optional<ProductQuestionResponse> result = productQuestionRepository.findProductQuestion(productQuestionId);
        result.orElseThrow(()-> new BusinessExceptionHandler("조회된 상품문의가 없습니다",ErrorCode.NO_ELEMENT_ERROR));
        return result.get();
    }

    @Override
    public void saveProductQuestion(ProductQuestionCreateRequest productQuestionRequest,Long writerId) {
        //상품 문의 객체 생성
        ProductQuestion productQuestion = ProductQuestion.builder()
                .writerId(writerId)
                .questionContent(productQuestionRequest.getQuestionContent())
                .product(Product.builder().productId(productQuestionRequest.getProductId()).build())
                .build();

        try {
            productQuestionRepository.save(productQuestion);
        } catch (Exception e) {
            throw new BusinessExceptionHandler("상품 문의 등록에 실패했습니다.", ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    public void updateProducQuestion(ProductQuestionUpdateRequest productQuestionRequest,Long sellerId) {
        //상품 문의 게시판 번호로 조회된 상품이 있는지 확인
        Long productQuestionId = productQuestionRequest.getProductQuestionBoardId();
        Optional<ProductQuestion> result = productQuestionRepository.findById(productQuestionId);

        try {
            ProductQuestion productQuestion = result.orElseThrow();

            //상품 문의 등록
            productQuestion.change(productQuestionRequest);
            productQuestionRepository.save(productQuestion);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("상품 문의가 존재하지 않습니다");
        }catch(Exception e){
            throw new BusinessExceptionHandler("상품 문의 답변 등록에 실패했습니다",ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteProducQuestion(Long productQuestionId,Long writerId) {
        //상품 번호로 상품 조회
        //TODO : 작성자와 동일한 아이디의 삭제요청인지 확인 필요
        ProductQuestion productQuestion = productQuestionRepository.findById(productQuestionId)
                .orElseThrow(() -> new BusinessExceptionHandler("상품 문의가 존재하지 않습니다", ErrorCode.NO_ELEMENT_ERROR));

        boolean equals = writerId.equals(productQuestion.getWriterId());
        //판매자 아이디랑 같은지 확인
        if (!equals) {
            throw new BusinessExceptionHandler("상품 문의 삭제 권한이 존재하지 않음",ErrorCode.DELETE_ERROR);
        }

        //문의 게시판 아이디로 문의 삭제
        productQuestionRepository.deleteById(productQuestionId);
    }
}
