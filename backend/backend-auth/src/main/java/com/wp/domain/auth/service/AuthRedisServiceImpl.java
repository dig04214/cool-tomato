package com.wp.domain.auth.service;

import com.wp.domain.auth.entity.JwtToken;
import com.wp.domain.auth.repository.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthRedisServiceImpl implements AuthRedisService{

    @Autowired
    JwtTokenRepository jwtTokenRepository;

    @Override
    public boolean registRefreshToken(String id, String refreshToken) {
        //이미 RT가 등록되어 있으면 false
        if(jwtTokenRepository.findById(id).orElse(null) != null)return false;
        JwtToken jwtToken = JwtToken.builder().id(id).refreshToken(refreshToken).build();
        JwtToken result = jwtTokenRepository.save(jwtToken);

        //RT 등록 성공 여부
        if(result.getId().equals(id) && result.getRefreshToken().equals(refreshToken)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public JwtToken getRefreshToken(String id) {
        //조회 성공시 JwtToken 객체 반환
        //조회 실패시 null 반환
        return jwtTokenRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateRefreshToken(String id, String refreshToken) {
        try {
            //업데이트 대상이 Redis에 존재하지 않으면 오류 발생
            JwtToken jwtToken = jwtTokenRepository.findById(id).orElseThrow();

            jwtToken.setRefreshToken(refreshToken);
            JwtToken result = jwtTokenRepository.save(jwtToken);
            //업데이트 성공 여부
            if(result.getRefreshToken().equals(refreshToken)){
                return true;
            }
            else {
                return false;
            }
        }catch (NoSuchElementException e){
            //업데이트 대상이 Redis에 존재하지 않을 때
            return false;
        }
    }

    @Override
    public boolean removeRefreshToken(String id) {
        JwtToken jwtToken;
        try {
            //삭제 대상이 Redis에 없을 때 오류 발생
            jwtToken = jwtTokenRepository.findById(id).orElseThrow();
        }catch (NoSuchElementException e){
            //삭제 대상이 없어도 목적 달성
            return true;
        }

        jwtTokenRepository.delete(jwtToken);
        //삭제 성공 여부
        try{
            jwtTokenRepository.findById(id).orElseThrow();
            return false;
        }catch (NoSuchElementException e){
            return true;
        }
    }
}
