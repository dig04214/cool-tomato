package com.wp.product.productquestion.repository;

import com.wp.product.productquestion.entity.ProductQuestion;
import com.wp.product.productquestion.repository.search.ProductQuestionSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion,Long>, ProductQuestionSearch {
    @Modifying
    @Query("delete from ProductQuestion where product.productId = :productId")
    void deleteProductQuestionByProductId(Long productId);
}
