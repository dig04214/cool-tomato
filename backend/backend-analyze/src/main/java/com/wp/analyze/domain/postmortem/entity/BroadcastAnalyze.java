package com.wp.analyze.domain.postmortem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "broadcast_analyze")
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastAnalyze {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "broadcast_analyze_id", nullable = false)
    private Long id;

    private Long liveBroadcastId;
    private String content;
}
