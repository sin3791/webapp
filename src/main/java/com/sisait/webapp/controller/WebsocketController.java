package com.sisait.webapp.controller;

import com.sisait.webapp.dto.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class WebsocketController {

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/chatting")
    public ChatMessageDTO addUser(
            @Payload ChatMessageDTO dto,
            SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("sender", dto.getSender());
        dto.setType(ChatMessageDTO.MessageType.JOIN);

        dto.setMessage(dto.getSender() + "님이 입장하셨습니다.");

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dto.setCreateDateTime(fmt.format(new Date()));

        System.out.println(dto.toString());

        return dto;
    }

    // 채팅 메시지 전송
    // 채팅 메시지 전송
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chatting")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO dto) {

        // ✅ 이 한 줄만 추가
        dto.setType(ChatMessageDTO.MessageType.CHAT);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dto.setCreateDateTime(fmt.format(new Date()));

        System.out.println(dto.toString());

        return dto;
    }
}