package com.wp.mediate.domain.openvidu.controller;

import com.wp.mediate.domain.openvidu.service.OpenviduService;
import io.openvidu.java.client.OpenViduRole;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/mediate/openvidu")
@Tag(name = "mediate/openvidu", description = "Openvidu 중개 API Doc")
public class OpenviduController {

    @Autowired
    OpenviduService openviduService;

    @ResponseBody
    @PostMapping("/session")
    public ResponseEntity<String> getSession(){
        String session = openviduService.createSession();
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @DeleteMapping("/session/{sessionId}")
    public HttpStatus removeSession(@PathVariable String sessionId){
        openviduService.removeSession(sessionId);
        return HttpStatus.OK;
    }

    @PostMapping("/token/{sessionId}/{role}")
    public ResponseEntity<String> getToken(@PathVariable String sessionId, @PathVariable String role){
        if(role.equals("판매자")){
            String token = openviduService.getToken(sessionId, OpenViduRole.PUBLISHER);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }else{
            String token = openviduService.getToken(sessionId, OpenViduRole.SUBSCRIBER);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }
}
