package com.wp.product.product.repository;

import com.wp.product.product.entity.Product;
import com.wp.product.product.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {
}
