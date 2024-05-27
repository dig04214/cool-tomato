package com.wp.product.liveproduct.repository;

import com.wp.product.liveproduct.entity.LiveProduct;
import com.wp.product.liveproduct.repository.search.LiveProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LiveProductRepository extends JpaRepository<LiveProduct,Long>, LiveProductSearch {
    //라이브 방송 아이디로 방송 상품 리스트 반환
    List<LiveProduct> findByLiveId(Long liveId);
    //라이브 방송 아이디로 방송 상품 삭제
    @Modifying
    @Query("delete from LiveProduct lp where lp.liveId = :liveId")
    void deleteByLiveId(Long liveId);
}
