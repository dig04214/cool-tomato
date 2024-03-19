package com.wp.product.category.repository;

import com.wp.product.category.entity.CategoryMaster;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMasterRepository extends JpaRepository<CategoryMaster, Id> {
}