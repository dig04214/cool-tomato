package com.wp.user.domain.user.repository.search;

import com.wp.user.domain.user.dto.response.GetUserResponse;

import java.util.Optional;

public interface UserSearchRepository {
    Optional<GetUserResponse> findUserById(Long userId);
}
