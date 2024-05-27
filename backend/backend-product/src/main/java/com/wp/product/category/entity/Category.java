package com.wp.product.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private Long categoryId;
    private String categoryCode;
    private String categoryContent;
    private int seq;
}