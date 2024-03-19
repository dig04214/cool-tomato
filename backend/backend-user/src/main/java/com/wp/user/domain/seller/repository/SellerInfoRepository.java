package com.wp.user.domain.seller.repository;

import com.wp.user.domain.seller.entity.SellerInfo;
import com.wp.user.domain.seller.repository.search.SellerSearchRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, Long>, SellerSearchRepository {
    @EntityGraph(attributePaths = {"user"})
    List<SellerInfo> findAll();
    @EntityGraph(attributePaths = {"user"})
    Optional<SellerInfo> findById(Long id);
}
