package com.wp.product.category.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMaster {
    @Id
    private Long categoryMasterId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="category_master_id")
    private List<Category> category;

    private String categoryMasterCode;
    private String categoryMasterContent;
    private int seq;
}