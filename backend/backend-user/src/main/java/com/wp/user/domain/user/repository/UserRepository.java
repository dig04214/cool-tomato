package com.wp.user.domain.user.repository;

import com.wp.user.domain.user.entity.Auth;
import com.wp.user.domain.user.entity.User;
import com.wp.user.domain.user.repository.search.UserSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserSearchRepository {
    // 로그인 아이디 중복 체크
    boolean existsByLoginId(String loginId);
    // 이메일 중복 체크
    boolean existsByEmail(String email);
    // 로그인 아이디로 회원 정보 찾기
    Optional<User> findUserByLoginId(String loginId);
    // 이메일로 로그인 아이디 찾기
    Optional<User> findUserByEmail(String email);
    // 로그인 아이디, 이메일로 회원 정보 찾기
    Optional<User> findUserByLoginIdAndEmail(String loginId, String email);
    // 권한 ADMIN이 아닌 회원 정보 찾기
    Page<User> findAllByAuthNot(Pageable pageable, Auth auth);
}
