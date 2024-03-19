package com.wp.user.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = '1')
    @Enumerated(value = EnumType.STRING)
    private Sex sex;

    @Column(nullable = false)
    private LocalDate birthday;

    private String profileImg;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime joinDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;
}
