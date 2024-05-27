package com.wp.chatbot.room.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    //채팅방 조회
    @GetMapping("/room")
    public String getRoom(){
        log.info("# get Chat Room, roomID : " + 1);
        return "room";
    }
}
