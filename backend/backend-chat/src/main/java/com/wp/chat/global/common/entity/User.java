package com.wp.chat.global.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
// 회원 기본 정보
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Auth auth;

    @Column(length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false, length = 50)
    private String nickname;

    private String profileImg;
}
