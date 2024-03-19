package com.wp.product.livebroadcast.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LiveBroadcast {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liveBroadcastId;
    private Long sellerId;
    private String broadcastTitle;
    private String content;
    private String script;
    private Boolean ttsSetting;
    private Boolean sttSetting;
    private Boolean chatbotSetting;
    private String shareUrl;
    private LocalDateTime broadcastStartDate;
    private LocalDateTime broadcastEndDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registerDate;

    private String broadcastStatus;
    private String viewCount;
}