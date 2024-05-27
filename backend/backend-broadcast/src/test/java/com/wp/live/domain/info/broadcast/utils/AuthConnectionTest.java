package com.wp.live.domain.info.broadcast.utils;

import com.wp.live.domain.broadcast.utils.AuthConnection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
class AuthConnectionTest {

    @Autowired
    AuthConnection authConnection;

    @Test
    void validateTest(){
        boolean result = authConnection.validationToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MDY1ODEyNjYsInN1YiI6ImFjY2Vzcy10b2tlbiIsInVzZXJJZCI6InRlc3QiLCJhdXRoIjoidGVzdCJ9.GGvhq87LgZGKET0JisgYRqhZGnfbLkmUxGYU6ECs9b0Ic8i6T0Y618aIANVxnPj5vK3tHlco55RLguGgDmc4iw");
        System.out.println(result);
    }

    @Test
    void getInfoTest(){
        ArrayList<String> strings = new ArrayList<>();
        strings.add("userId");
        strings.add("auth");
        Map<String, Map<String, String>> info = authConnection.getInfo("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MDY1ODY4NDEsInN1YiI6ImFjY2Vzcy10b2tlbiIsInVzZXJJZCI6InRlc3QiLCJhdXRoIjoidGVzdCJ9.qX5QRU2eNIDo20uV_KnCvkat_UHouOCEBD3WjtUuLNapjIi76SdTvyvrv-LvBXH5RElZH6yxizccffE0tod01g", strings);
        System.out.println(info.get("infos").get("userId"));
        System.out.println(info.get("infos").get("auth"));
    }

}