package com.wp.product.liveproduct.service;

import com.wp.product.global.common.code.ErrorCode;
import com.wp.product.global.exception.BusinessExceptionHandler;
import com.wp.product.global.util.DateUtil;
import com.wp.product.liveproduct.dto.request.LiveProductCreateRequest;
import com.wp.product.liveproduct.dto.request.LiveProductSearchRequest;
import com.wp.product.liveproduct.dto.response.LiveBroadcastProductResponse;
import com.wp.product.liveproduct.dto.response.LiveProductResponse;
import com.wp.product.liveproduct.entity.LiveProduct;
import com.wp.product.liveproduct.repository.LiveProductRepository;
import com.wp.product.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveProductServiceImpl implements LiveProductService{

    private final LiveProductRepository liveProductRepository;

    @Override
    public List<LiveBroadcastProductResponse> findLiveBroadcastProduct() {
        return liveProductRepository.searchLiveBroadcastProduct();
    }

    @Override
    @Transactional
    public Map<String, Object> findLiveProduct(LiveProductSearchRequest request) {
        try {
            Map<String, Object> map = new HashMap<>();
            Page<LiveProductResponse> result = liveProductRepository.search(request);

            map.put("list", result.getContent());
            map.put("totalCount", result.getTotalElements());

            return map;
        }catch (Exception e){
            log.debug(e.getMessage());
            throw new BusinessExceptionHandler("라이브 방송 상품 목록 조회 중 에러가 발생했습니다.",ErrorCode.NO_ELEMENT_ERROR);
        }
    }

    @Override
    public void saveLiveProduct(List<LiveProductCreateRequest> liveProductRequestList) {
        List<LiveProduct> liveProductList = new ArrayList<>();

        //라이브 상품 리스트 요청을 엔터티로 매핑
        liveProductRequestList.forEach(liveProductCreateRequest -> {
            liveProductList.add(LiveProduct.builder()
                    .product(Product.builder().productId(liveProductCreateRequest.getProductId()).build())
                    .liveId(liveProductCreateRequest.getLiveId())
                    .liveFlatPrice(liveProductCreateRequest.getLiveFlatPrice())
                    .liveRatePrice(liveProductCreateRequest.getLiveRatePrice())
                    .livePriceStartDate(DateUtil.stringToLocalDateTime(liveProductCreateRequest.getLivePriceStartDate()))
                    .livePriceEndDate(DateUtil.stringToLocalDateTime(liveProductCreateRequest.getLivePriceEndDate()))
                    .mainProductSetting(liveProductCreateRequest.getMainProductSetting())
                    .seq(liveProductCreateRequest.getSeq()).build());
        });

        try {
            //방송 상품 저장
            liveProductRepository.saveAll(liveProductList);
        }catch (Exception e){
            throw new BusinessExceptionHandler("라이브 상품 등록에 실패했습니다.", ErrorCode.INSERT_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteLiveProduct(Long liveId) {
        //라이브 방송 아이디로 방송상품 조회
        List<LiveProduct> productList = liveProductRepository.findByLiveId(liveId);
        //조회된 리스트가 없는 경우
        if(productList.size() == 0) {
            throw new BusinessExceptionHandler("존재하지 않는 방송 아이디입니다", ErrorCode.NO_ELEMENT_ERROR);
        }

        try {
            //방송 상품 삭제
            liveProductRepository.deleteByLiveId(liveId);
        }catch (Exception e){
            throw new BusinessExceptionHandler("방송 상품 삭제 중 에러 발생",ErrorCode.DELETE_ERROR);
        }
    }
}
