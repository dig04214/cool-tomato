package com.wp.user.domain.follow.dto.response;

import com.wp.user.domain.follow.entity.FollowManage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "팔로우 목록 조회를 위한 응답 객체")
public class GetFollowManageListResponse {
    List<GetFollowManage> follow;
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetFollowManage {
        private Long id;
        private Long userId;
        private String loginId;
        private String nickname;
        private String profileImg;
    }

    public static GetFollowManageListResponse fromFollower(List<FollowManage> followManageList) {
        List<GetFollowManage> follow = new ArrayList<>();
        for (FollowManage followManage : followManageList) {
            GetFollowManage getFollowManage = GetFollowManage.builder()
                    .id(followManage.getId())
                    .userId(followManage.getFollower().getId())
                    .loginId(followManage.getFollower().getLoginId())
                    .nickname(followManage.getFollower().getNickname())
                    .profileImg(followManage.getFollower().getProfileImg()).build();
            follow.add(getFollowManage);
        }
        return GetFollowManageListResponse.builder().follow(follow).build();
    }

    public static GetFollowManageListResponse fromFollowing(List<FollowManage> followManageList) {
        List<GetFollowManage> follow = new ArrayList<>();
        for (FollowManage followManage : followManageList) {
            GetFollowManage getFollowManage = GetFollowManage.builder()
                    .id(followManage.getId())
                    .userId(followManage.getFollowing().getId())
                    .loginId(followManage.getFollowing().getLoginId())
                    .nickname(followManage.getFollowing().getNickname())
                    .profileImg(followManage.getFollowing().getProfileImg()).build();
            follow.add(getFollowManage);
        }
        return GetFollowManageListResponse.builder().follow(follow).build();
    }
}
