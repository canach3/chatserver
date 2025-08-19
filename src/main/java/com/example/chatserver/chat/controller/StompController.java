package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatMessageRequest;
import com.example.chatserver.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompController {
    private final SimpMessageSendingOperations messageTemplate;
    private final ChatService chatService;

//    // 방법 1 : MessageMapping(수신)과 SendTo(topic에 메시지 전달) 한 꺼번에 처리
//    @MessageMapping("/{roomId}") // 클라이언트에서 특정 publish/roomId 형태로 메시지 발행시 MessageMapping이 그것을 수신
//    @SendTo("/topic/{roomId}") // 해당 roomId에 메시지를 발행하여 구독중인 클라이언트에게 메시지 전송
//    // @DestinationVariable : @MessageMapping 어노테이션으로 정의된 Websocket Controller 내에서만 사용됨(@PathVariable과 비슷)
//    public String sendMessage(@DestinationVariable Long roomId, String message) {
//        System.out.println(message);
//
//        return message;
//    }

    // 방법 2 : MessageMapping 어노테이션만 활용
    @MessageMapping("/{roomId}")
    // @DestinationVariable : @MessageMapping 어노테이션으로 정의된 Websocket Controller 내에서만 사용됨(@PathVariable과 비슷)
    public void sendMessage(@DestinationVariable Long roomId, ChatMessageRequest chatMessageRequest) {
        System.out.println(chatMessageRequest);
        chatService.saveMessage(roomId, chatMessageRequest);
        messageTemplate.convertAndSend("/topic/" + roomId, chatMessageRequest);
    }
}
