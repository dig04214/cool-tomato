package com.wp.user.domain.seller.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wp.user.domain.user.entity.Auth;
import com.wp.user.domain.user.entity.Sex;
import com.wp.user.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SellerInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "seller_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(length = 100)
    private String businessNumber;

    @Lob
    private String businessContent;

    @Column(length = 100)
    private String mailOrderSalesNumber;

    private String businessAddress;

    @Column(length = 20)
    private String phoneNumber;

    private boolean approvalStatus;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registerDate;
}
