package com.wp.user.domain.alarm.entity;

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
// 알림 관리
public class AlarmManage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "alarm_manage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private String alarmTitle;

    private String alarmContent;

    private String alarmUrl;

    private boolean status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime registerDate;
}
