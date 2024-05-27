package com.wp.user.domain.alarm.repository;

import com.wp.user.domain.alarm.entity.AlarmToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlarmTokenRepository extends CrudRepository<AlarmToken, Long> {
}
