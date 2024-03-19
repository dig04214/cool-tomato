package com.wp.product.product.entity;

import com.wp.product.category.entity.Category;
import com.wp.product.product.dto.request.ProductUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    private Long sellerId;
    private String productName;
    private String productContent;
    private String paymentLink;
    private String imgSrc;
    private int price;
    private int deliveryCharge;
    private int quantity;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registerDate;

    public void change(ProductUpdateRequest productRequest) {
        this.productName = productRequest.getProductName();
        this.productContent = productRequest.getProductContent();
        this.paymentLink = productRequest.getPaymentLink();
        this.price = productRequest.getPrice();
        this.deliveryCharge = productRequest.getDeliveryCharge();
        this.quantity = productRequest.getQuantity();
    }

    public void changeImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
