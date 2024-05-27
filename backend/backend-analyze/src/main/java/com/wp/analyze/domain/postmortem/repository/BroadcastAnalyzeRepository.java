package com.wp.analyze.domain.postmortem.repository;

import com.wp.analyze.domain.postmortem.entity.BroadcastAnalyze;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface BroadcastAnalyzeRepository extends JpaRepository<BroadcastAnalyze, Long> {
    Optional<BroadcastAnalyze> findByLiveBroadcastId(Long liveBroadcastId);
}
