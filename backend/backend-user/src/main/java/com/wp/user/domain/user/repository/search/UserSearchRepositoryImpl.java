package com.wp.user.domain.user.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wp.user.domain.user.dto.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.wp.user.domain.seller.entity.QSellerInfo.sellerInfo;
import static com.wp.user.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserSearchRepositoryImpl implements UserSearchRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<GetUserResponse> findUserById(Long userId) {
        return Optional.ofNullable(jpaQueryFactory.select(Projections.bean(GetUserResponse.class,
                user.id,
                user.loginId,
                user.nickname,
                user.sex,
                user.birthday,
                user.profileImg,
                user.auth,
                user.joinDate,
                Projections.bean(GetUserResponse.SellerInfo.class,
                        sellerInfo.id.as("sellerInfoId"),
                        sellerInfo.businessNumber,
                        sellerInfo.businessContent,
                        sellerInfo.businessAddress,
                        sellerInfo.phoneNumber,
                        sellerInfo.registerDate,
                        sellerInfo.approvalStatus).as("sellerInfo")
                ))
                .from(user)
                .leftJoin(sellerInfo)
                .on(user.id.eq(sellerInfo.user.id))
                .fetchJoin()
                .where(user.id.eq(userId))
                .orderBy(sellerInfo.registerDate.desc())
                .limit(1)
                .fetchFirst());
    }
}
