package com.wp.live.domain.broadcast.repository;

import com.wp.live.domain.broadcast.entity.LiveBroadcast;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional
public interface LiveBroadcastRepository extends JpaRepository<LiveBroadcast, Long> {
    @EntityGraph(attributePaths = {"user"})
    public Optional<LiveBroadcast> findById(Long id);
    @EntityGraph(attributePaths = {"user"})
    public Page<LiveBroadcast> findAllByBroadcastStatus(Boolean broadcastStatus, Pageable pageable);
    public Page<LiveBroadcast> findByUserIdInAndIsDeleted(Collection<Long> id, Boolean isDeleted, Pageable pageable);
    @EntityGraph(attributePaths = {"user"})
    public Page<LiveBroadcast> findByUserIdAndIsDeleted(Long id, Boolean isDeleted, Pageable pageable);
    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT lb FROM LiveBroadcast lb WHERE FUNCTION('DATE_FORMAT', lb.broadcastStartDate, '%y-%m-%d') = :startDate AND lb.isDeleted = false ORDER BY lb.broadcastStartDate")
    public Page<LiveBroadcast> findLiveBroadcastsByStartDateAndNotDeleted(@Param("startDate") String startDate, Pageable pageable);
}
