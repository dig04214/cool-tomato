package com.wp.live.domain.broadcast.repository;

import com.wp.live.domain.broadcast.entity.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserSearchRepository extends ElasticsearchRepository<UserDocument, Long> {
    public List<UserDocument> findByNicknameContaining(String nickName);
}
