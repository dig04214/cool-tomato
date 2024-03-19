package com.wp.user.domain.alarm.repository;

import com.wp.user.domain.alarm.entity.AlarmManage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmManageRepository extends JpaRepository<AlarmManage, Long>  {
    List<AlarmManage> findAllByUserId(Long userId);
}
