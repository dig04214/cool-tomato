package com.wp.user.domain.follow.entity;

import com.wp.user.domain.user.entity.User;
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
public class FollowManage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "follow_manage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

    private boolean followAlarmSetting;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registerDate;
}
