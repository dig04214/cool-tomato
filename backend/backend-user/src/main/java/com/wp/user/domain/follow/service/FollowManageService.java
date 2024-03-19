package com.wp.user.domain.follow.service;

import com.wp.user.domain.follow.dto.request.AddFollowManageRequest;
import com.wp.user.domain.follow.dto.response.FollowStatusResponse;
import com.wp.user.domain.follow.dto.response.GetFollowManageListResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface FollowManageService {
    GetFollowManageListResponse getFollowerManages(HttpServletRequest httpServletRequest);
    GetFollowManageListResponse getFollowingManages(HttpServletRequest httpServletRequest);
    FollowStatusResponse getFollowStatus(HttpServletRequest httpServletRequest, Long sellerId);
    void addFollow(HttpServletRequest httpServletRequest, AddFollowManageRequest addFollowManageRequest);
    void removeFollow(HttpServletRequest httpServletRequest, Long sellerId);
}
