package com.wp.product.productquestion.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wp.product.product.entity.QProduct;
import com.wp.product.productquestion.dto.request.ProductQuestionSearchRequest;
import com.wp.product.productquestion.dto.response.ProductQuestionResponse;
import com.wp.product.productquestion.dto.response.ProductQuestionSearchResponse;
import com.wp.product.productquestion.entity.ProductQuestion;
import com.wp.product.productquestion.entity.QProductQuestion;
import com.wp.product.user.entity.QUser;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductQuestionSearchImpl extends QuerydslRepositorySupport implements ProductQuestionSearch {
    private final JPAQueryFactory queryFactory;

    public ProductQuestionSearchImpl(JPAQueryFactory queryFactory){
        super(ProductQuestion.class);
        this.queryFactory = queryFactory;
    }
    @Override
    @Transactional
    public Page<ProductQuestionSearchResponse> search(ProductQuestionSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());    //페이징

        QProductQuestion qProductQuestionBoard = QProductQuestion.productQuestion;
        QUser qUser = QUser.user;

        List<ProductQuestionSearchResponse> list = queryFactory.select(Projections.bean(ProductQuestionSearchResponse.class,
                                qProductQuestionBoard.productQuestionBoardId,
                                qProductQuestionBoard.writerId,
                                qUser.nickname.as("writerNickname"),
                                qProductQuestionBoard.product.productId,
                                qProductQuestionBoard.product.imgSrc,
                                qProductQuestionBoard.questionContent,
                                qProductQuestionBoard.answerContent,
                                qProductQuestionBoard.questionRegisterDate,
                                qProductQuestionBoard.answerRegisterDate,
                                new CaseBuilder()
                                        .when(qProductQuestionBoard.answerContent.isNotNull())
                                        .then(1)
                                        .otherwise(0).as("answer")
                ))
                .from(qProductQuestionBoard)
                .leftJoin(qUser)
                .on(qUser.userId.eq(qProductQuestionBoard.writerId))
                .fetchJoin()
                .where(qProductQuestionBoard.product.productId.eq(request.getProductId()))
                .offset(request.getPage()*request.getSize())
                .limit(request.getSize())
                .orderBy(qProductQuestionBoard.questionRegisterDate.desc())
                .fetch();

        JPQLQuery<Long> countQuery = queryFactory.select(qProductQuestionBoard.count())
                .from(qProductQuestionBoard)
                .where(qProductQuestionBoard.product.productId.eq(request.getProductId()));

        return PageableExecutionUtils.getPage(list,pageable,countQuery::fetchOne);
    }

    @Override
    public Optional<ProductQuestionResponse> findProductQuestion(Long productQuestionId) {
        QProductQuestion qProductQuestionBoard = QProductQuestion.productQuestion;
        QUser qUser = QUser.user;

        Optional<ProductQuestionResponse> result = Optional.ofNullable(queryFactory.select(Projections.bean(ProductQuestionResponse.class,
                        qProductQuestionBoard.productQuestionBoardId,
                        qProductQuestionBoard.writerId,
                        qUser.nickname.as("writerNickname"),
                        qProductQuestionBoard.product.productId,
                        qProductQuestionBoard.product.sellerId,
                        qProductQuestionBoard.product.imgSrc,
                        qProductQuestionBoard.product.productContent,
                        qProductQuestionBoard.product.productName,
                        qProductQuestionBoard.questionContent,
                        qProductQuestionBoard.answerContent,
                        qProductQuestionBoard.questionRegisterDate,
                        qProductQuestionBoard.answerRegisterDate,
                        new CaseBuilder()
                                .when(qProductQuestionBoard.answerContent.isNotNull())
                                .then(1)
                                .otherwise(0).as("answer")
                ))
                .from(qProductQuestionBoard)
                .leftJoin(qUser)
                .on(qUser.userId.eq(qProductQuestionBoard.writerId))
                .fetchJoin()
                .where(qProductQuestionBoard.productQuestionBoardId.eq(productQuestionId))
                .fetchOne());

        return result;
    }

    @Override
    @Transactional
    public Page<ProductQuestionMyListResponse> getMyProductQuestionList(ProductQuestionSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());    //페이징

        QProductQuestion qProductQuestionBoard = QProductQuestion.productQuestion;
        QUser qWriter = QUser.user;
        QUser qSeller = QUser.user;
        QProduct product = QProduct.product;

        List<ProductQuestionMyListResponse> list = queryFactory.select(Projections.bean(ProductQuestionMyListResponse.class,
                        qProductQuestionBoard.productQuestionBoardId,
                        qProductQuestionBoard.writerId,
                        qWriter.nickname.as("writerNickname"),
                        qProductQuestionBoard.product.productId,
                        qProductQuestionBoard.product.imgSrc,
                        qProductQuestionBoard.product.productName,
                        qProductQuestionBoard.product.productContent,
                        qProductQuestionBoard.questionContent,
                        qProductQuestionBoard.answerContent,
                        qProductQuestionBoard.questionRegisterDate,
                        qProductQuestionBoard.answerRegisterDate,
                        new CaseBuilder()
                                .when(qProductQuestionBoard.answerContent.isNotNull())
                                .then(1)
                                .otherwise(0).as("answer")
                ))
                .from(qProductQuestionBoard)
                .leftJoin(qWriter)
                .on(qWriter.userId.eq(qProductQuestionBoard.writerId))  //작성사 정보로 닉네임 조회하기 위함
                .leftJoin(qSeller)
                .on(qSeller.userId.eq(product.sellerId))        //판매자 정보로 상품 조회하기 위함
                .where(qProductQuestionBoard.product.sellerId.eq(request.getSellerId()))
                .where(product.sellerId.eq(request.getSellerId()))
                .offset(request.getPage()*request.getSize())
                .limit(request.getSize())
                .orderBy(qProductQuestionBoard.answerContent.asc(),qProductQuestionBoard.questionRegisterDate.desc())
                .fetch();

        JPQLQuery<Long> countQuery = queryFactory.select(qProductQuestionBoard.count())
                .from(qProductQuestionBoard)
                .where(qProductQuestionBoard.product.sellerId.eq(request.getSellerId()));

        return PageableExecutionUtils.getPage(list,pageable,countQuery::fetchOne);
    }

    @Override
    @Transactional
    public Page<ProductQuestionMyListResponse> getMyQuestionList(ProductQuestionSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());    //페이징

        QProductQuestion qProductQuestionBoard = QProductQuestion.productQuestion;
        QUser qUser = QUser.user;

        List<ProductQuestionMyListResponse> list = queryFactory.select(Projections.bean(ProductQuestionMyListResponse.class,
                        qProductQuestionBoard.productQuestionBoardId,
                        qProductQuestionBoard.writerId,
                        qUser.nickname.as("writerNickname"),
                        qProductQuestionBoard.product.productId,
                        qProductQuestionBoard.product.imgSrc,
                        qProductQuestionBoard.product.productName,
                        qProductQuestionBoard.product.productContent,
                        qProductQuestionBoard.questionContent,
                        qProductQuestionBoard.answerContent,
                        qProductQuestionBoard.questionRegisterDate,
                        qProductQuestionBoard.answerRegisterDate,
                        new CaseBuilder()
                                .when(qProductQuestionBoard.answerContent.isNotNull())
                                .then(1)
                                .otherwise(0).as("answer")
                ))
                .from(qProductQuestionBoard)
                .leftJoin(qUser)
                .on(qUser.userId.eq(qProductQuestionBoard.writerId))
                .fetchJoin()
                .where(qProductQuestionBoard.writerId.eq(request.getBuyerId()))
                .offset(request.getPage()*request.getSize())
                .limit(request.getSize())
                .orderBy(qProductQuestionBoard.answerContent.asc(),qProductQuestionBoard.questionRegisterDate.desc())
                .fetch();

        JPQLQuery<Long> countQuery = queryFactory.select(qProductQuestionBoard.count())
                .from(qProductQuestionBoard)
                .where(qProductQuestionBoard.writerId.eq(request.getBuyerId()));

        return PageableExecutionUtils.getPage(list,pageable,countQuery::fetchOne);
    }
}
