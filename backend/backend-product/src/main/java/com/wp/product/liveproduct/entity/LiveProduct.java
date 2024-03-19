package com.wp.product.liveproduct.entity;

import com.wp.product.product.entity.Product;
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
public class LiveProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liveProductId;

    private Long liveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int liveFlatPrice;
    private int liveRatePrice;

    private LocalDateTime livePriceStartDate;
    private LocalDateTime livePriceEndDate;
    private Boolean mainProductSetting;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registerDate;
    private int seq;
}
