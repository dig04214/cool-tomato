package com.wp.user.domain.seller.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wp.user.domain.seller.dto.response.GetSellerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import static com.wp.user.domain.follow.entity.QFollowManage.followManage;
import static com.wp.user.domain.seller.entity.QSellerInfo.sellerInfo;
import static com.wp.user.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class SellerSearchRepositoryImpl implements SellerSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<GetSellerResponse> findSellerByUserId(Long userId) {
        return Optional.ofNullable(jpaQueryFactory.select(Projections.bean(GetSellerResponse.class,
                user.id.as("userId"),
                user.loginId,
                user.nickname,
                user.sex,
                user.birthday,
                user.profileImg,
                user.auth,
                user.joinDate,
                sellerInfo.id.as("sellerInfoId"),
                followManage.following.count().as("followerCount"))
                )
                .from(user)
                .leftJoin(sellerInfo)
                .on(user.id.eq(sellerInfo.user.id))
                .fetchJoin()
                .leftJoin(followManage)
                .on(user.id.eq(followManage.following.id))
                .fetchJoin()
                .where(user.id.eq(userId))
                .groupBy(user.id, sellerInfo.id)
                .orderBy(sellerInfo.registerDate.desc())
                .limit(1)
                .fetchFirst());
    }
}
