package com.wp.user.domain.follow.repository;

import com.wp.user.domain.follow.entity.FollowManage;
import com.wp.user.domain.user.entity.User;
import feign.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowManageRepository extends JpaRepository<FollowManage, Long> {
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
    @EntityGraph(attributePaths = {"following"})
    List<FollowManage> findAllByFollowerId(Long followerId);
    @EntityGraph(attributePaths = {"follower"})
    List<FollowManage> findAllByFollowingId(Long sellerId);
    @Query("SELECT e.follower FROM FollowManage e WHERE e.following.id = :sellerId")

    List<User> findAllFollowerByFollowingId(@Param("sellerId") Long sellerId);
}
