package com.wp.chat.domain.block.entity;

import com.wp.chat.global.common.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlockManage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "block_manage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User blocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registerDate;
}