package com.wp.user.domain.user.repository;

import com.wp.user.domain.user.entity.EmailCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCodeRepository extends CrudRepository<EmailCode, String> {

}
