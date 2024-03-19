package com.wp.chat.global.common.repository;

import com.wp.chat.global.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
