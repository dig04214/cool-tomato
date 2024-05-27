package com.wp.live.domain.broadcast.repository;

import com.wp.live.domain.broadcast.entity.LiveBroadcastDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.domain.Pageable;

public interface LiveBroadcastSearchRepository extends ElasticsearchRepository<LiveBroadcastDocument, Long> {
    public Page<LiveBroadcastDocument> findByBroadcastTitleContainingAndIsDeleted(String broadcastTitle, Boolean isDeleted, Pageable pageable);
}
