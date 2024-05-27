package com.wp.chat.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// 테스트용 Controller
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
}
