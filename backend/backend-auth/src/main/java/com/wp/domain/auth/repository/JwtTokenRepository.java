package com.wp.domain.auth.repository;

import com.wp.domain.auth.entity.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {

}
