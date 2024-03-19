package com.wp.user.domain.user.dto.response;

import com.wp.user.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "전체 회원 정보 조회를 위한 응답 객체")
public class GetUserListResponse {
    int totalPage;
    long totalSize;
    List<User> users;
}
