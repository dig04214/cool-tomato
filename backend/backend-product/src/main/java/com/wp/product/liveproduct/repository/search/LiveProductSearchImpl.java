package com.wp.product.liveproduct.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wp.product.livebroadcast.entity.QLiveBroadcast;
import com.wp.product.liveproduct.dto.request.LiveProductSearchRequest;
import com.wp.product.liveproduct.dto.response.LiveBroadcastProductResponse;
import com.wp.product.liveproduct.dto.response.LiveProductResponse;
import com.wp.product.liveproduct.entity.LiveProduct;
import com.wp.product.liveproduct.entity.QLiveProduct;
import com.wp.product.product.entity.QProduct;
import com.wp.product.user.entity.QUser;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class LiveProductSearchImpl extends QuerydslRepositorySupport implements LiveProductSearch{

    private final JPAQueryFactory queryFactory;

    public LiveProductSearchImpl(JPAQueryFactory queryFactory) {
        super(LiveProduct.class);
        this.queryFactory = queryFactory;
    }

    @Override
    @Transactional
    public Page<LiveProductResponse> search(LiveProductSearchRequest request){
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        QLiveProduct liveProduct = QLiveProduct.liveProduct;
        QProduct product = QProduct.product;
        QUser user = QUser.user;

        //방송 상품 목록 조회 쿼리
        List<LiveProductResponse> list = queryFactory.select(Projections.bean(LiveProductResponse.class,
                liveProduct.liveProductId,
                        product.productId,
                        product.imgSrc,
                        product.sellerId,
                        user.nickname.as("sellerName"),
                        user.profileImg.as("sellerImg"),
                        product.category.categoryId,
                        product.category.categoryContent.as("categoryName"),
                        product.productName,
                        product.productContent,
                        product.price,
                        product.deliveryCharge,
                        product.quantity,
                        liveProduct.liveFlatPrice,
                        liveProduct.liveRatePrice,
                        liveProduct.livePriceStartDate,
                        liveProduct.livePriceEndDate,
                        liveProduct.mainProductSetting,
                        liveProduct.registerDate,
                        liveProduct.seq))
                .from(liveProduct)
                .innerJoin(product)
                .on(liveProduct.product.productId.eq(product.productId))
                .fetchJoin()
                .leftJoin(user)
                .on(product.sellerId.eq(user.userId))
                .where(liveProduct.liveId.eq(request.getLiveId()))
                .orderBy(liveProduct.mainProductSetting.desc(),liveProduct.seq.asc(),liveProduct.registerDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //갯수 조회 쿼리
        JPQLQuery<Long> countQuery = queryFactory.select(liveProduct.count())
                .from(liveProduct)
                .where(liveProduct.liveId.eq(request.getLiveId()));

        return PageableExecutionUtils.getPage(list,pageable,countQuery::fetchOne);
    }

    @Override
    @Transactional
    public List<LiveBroadcastProductResponse> searchLiveBroadcastProduct(){
        QProduct qProduct = QProduct.product;
        QLiveProduct qLiveProduct = QLiveProduct.liveProduct;
        QLiveBroadcast qLiveBroadcast = QLiveBroadcast.liveBroadcast;

        //라이브 중인 방송 상품 조회
        List<LiveBroadcastProductResponse> list  = queryFactory.select(Projections.bean(LiveBroadcastProductResponse.class,
                        qLiveProduct.liveProductId,
                        qLiveBroadcast.liveBroadcastId,
                        qProduct.productId,
                        qProduct.imgSrc,
                        qProduct.category.categoryId,
                        qProduct.category.categoryContent.as("categoryName"),
                        qProduct.sellerId,
                        qProduct.productName,
                        qProduct.productContent,
                        qProduct.paymentLink,
                        qProduct.price,
                        qProduct.deliveryCharge,
                        qProduct.quantity,
                        qProduct.registerDate,
                        qLiveProduct.liveFlatPrice,
                        qLiveProduct.liveRatePrice,
                        qLiveProduct.livePriceStartDate,
                        qLiveProduct.livePriceEndDate,
                        qLiveProduct.mainProductSetting,
                        qLiveBroadcast.broadcastStatus,
                        qLiveBroadcast.liveBroadcastId
                ))
                .from(qProduct)
                .leftJoin(qLiveProduct)
                .on(qProduct.productId.eq(qLiveProduct.product.productId))
                .leftJoin(qLiveBroadcast)
                .on(qLiveProduct.liveId.eq(qLiveBroadcast.liveBroadcastId))
                .where(qLiveBroadcast.broadcastStatus.eq("1"))
                .orderBy(qLiveBroadcast.broadcastStartDate.desc(), qProduct.productId.desc())   //가장 최근 등록한 방송,상품 순
                .fetch();

        return list;
    }
}
