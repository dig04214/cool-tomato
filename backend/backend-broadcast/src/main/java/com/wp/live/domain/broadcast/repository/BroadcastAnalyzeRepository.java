package com.wp.live.domain.broadcast.repository;

import com.wp.live.domain.broadcast.entity.BroadcastAnalyze;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BroadcastAnalyzeRepository extends JpaRepository<BroadcastAnalyze, Long> {
}
