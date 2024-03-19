package com.wp.product.product.service;

import com.wp.product.category.entity.Category;
import com.wp.product.global.common.code.ErrorCode;
import com.wp.product.global.exception.BusinessExceptionHandler;
import com.wp.product.global.file.service.S3UploadService;
import com.wp.product.liveproduct.repository.LiveProductRepository;
import com.wp.product.product.dto.request.ProductCreateRequest;
import com.wp.product.product.dto.request.ProductSearchRequest;
import com.wp.product.product.dto.request.ProductUpdateRequest;
import com.wp.product.product.dto.response.ProductFindResponse;
import com.wp.product.product.entity.Product;
import com.wp.product.product.repository.ProductRepository;
import com.wp.product.productquestion.repository.ProductQuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductQuestionRepository productQuestionRepository;
    private final LiveProductRepository liveProductRepository;
    private final S3UploadService s3UploadService;

    @Override
    public Map<String, Object> searchProduct(ProductSearchRequest productSearchRequest) {
        //검색 조건에 맞는 상품 리스트 조회
        try {
            Page<ProductFindResponse> result = productRepository.search(productSearchRequest);
            Map<String, Object> map = new HashMap<>();

            map.put("list", result.getContent());
            map.put("totalCount", result.getTotalElements());

            return map;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessExceptionHandler("상품 리스트 조회 중 에러가 발생했습니다.", ErrorCode.NO_ELEMENT_ERROR);
        }
    }

    @Override
    public Map<String, Object> searchMyProducts(ProductSearchRequest productSearchRequest) {
        //판매자가 자신이 등록한 상품 리스트 조회
        try {
            Page<ProductFindResponse> result = productRepository.searchMyProducts(productSearchRequest);
            Map<String, Object> map = new HashMap<>();

            map.put("list", result.getContent());
            map.put("totalCount", result.getTotalElements());

            return map;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessExceptionHandler("상품 리스트 조회 중 에러가 발생했습니다.", ErrorCode.NO_ELEMENT_ERROR);
        }
    }

    @Override
    public Map<String, Object> searchRecentProducts(List<Long> idList) {
        try {
            //상품 아이디로 최근 본 상품 리스트 조회
            Page<ProductFindResponse> result = productRepository.searchRecentProducts(idList);
            Map<String, Object> map = new HashMap<>();

            map.put("list" , result.getContent());
            map.put("totalCount", result.getTotalElements());

            return map;
        }catch (Exception e){
            log.debug(e.getMessage());
            throw new BusinessExceptionHandler("유효하지 않은 상품 번호입니다.",ErrorCode.NO_ELEMENT_ERROR);
        }
    }

    @Override
    public ProductFindResponse findProductById(Long productId) {
        //상품 번호로 상품 조회
        Optional<ProductFindResponse> result = productRepository.searchByProductId(productId);

        //상품이 존재하지 않으면 예외처리
        ProductFindResponse product = result
                            .orElseThrow(()->new BusinessExceptionHandler("상품이 존재하지 않습니다",ErrorCode.NO_ELEMENT_ERROR));

        return product;
    }

    @Override
    @Transactional
    public void saveProduct(ProductCreateRequest productRequest, Long userId, MultipartFile file){
        String imgSrc = "";
        imgSrc = s3UploadService.saveFile(file);

        //상품 등록 객체 생성
        Product product = Product.builder()
                        .category(Category.builder().categoryId(productRequest.getCategoryId()).build())
                        .sellerId(userId)
                        .productName(productRequest.getProductName())
                        .productContent(productRequest.getProductContent())
                        .imgSrc(imgSrc)
                        .paymentLink(productRequest.getPaymentLink())
                        .price(productRequest.getPrice())
                        .deliveryCharge(productRequest.getDeliveryCharge())
                        .quantity(productRequest.getQuantity())
                        .build();
        try {
            //상품을 등록함
            productRepository.save(product);
        }catch (DataIntegrityViolationException e){
            log.debug(e.getMessage());
            throw new DataIntegrityViolationException("상품 등록에 실패했습니다");
        }
    }

    @Override
    @Transactional
    public void updateProduct(ProductUpdateRequest productRequest, Long userId, MultipartFile file) {
        //상품번호로 조회된 상품이 있는지 확인
        Long productId = productRequest.getProductId();
        //TODO : 작성자와 동일한 아이디의 수정요청인지 확인 필요
        Optional<Product> result = productRepository.findById(productId);

        try {
            Product product = result.orElseThrow();

            boolean equals = userId.equals(product.getSellerId());
            //판매자 아이디랑 같은지 확인
            if (!equals) {
                throw new Exception();
            }

            //파일이 있다가 삭제됨
            if((product.getImgSrc() != null && "".equals(product.getImgSrc())) && "".equals(productRequest.getImgSrc())){
                s3UploadService.deleteImage(product.getImgSrc());
            }

            //새로운 파일 등록
            if (file !=null){
                String imgSrc = s3UploadService.saveFile(file);
                product.changeImgSrc(imgSrc);
            }
            //상품 정보 수정
            product.change(productRequest);
            productRepository.save(product);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("상품이 존재하지 않습니다");
        }catch(Exception e){
            log.debug(e.getMessage());
            throw new BusinessExceptionHandler("상품 수정에 실패했습니다",ErrorCode.UPDATE_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId, Long userId) {
        //상품 번호로 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessExceptionHandler("상품이 존재하지 않습니다", ErrorCode.NO_ELEMENT_ERROR));

        try{
            boolean equals = userId.equals(product.getSellerId());
            //판매자 아이디랑 같은지 확인
            if(!equals){
                throw new Exception();
            }

            //상품 문의 먼저 삭제
            productQuestionRepository.deleteProductQuestionByProductId(productId);

//            //라이브 상품 삭제
//            liveProductRepository.deleteById(productId);
//
            //이미지 삭제
            s3UploadService.deleteImage(product.getImgSrc());

            //상품 번호로 상품 삭제
            productRepository.deleteById(productId);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new BusinessExceptionHandler("삭제 중 에러가 발생했습니다",ErrorCode.BAD_REQUEST_ERROR);
        }
    }

    @Override
    public String saveProductFile(MultipartFile file) {
        return s3UploadService.saveFile(file);
    }
}
