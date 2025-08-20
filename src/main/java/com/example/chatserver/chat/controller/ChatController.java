package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatHistoryResponse;
import com.example.chatserver.chat.dto.ChatRoomResponse;
import com.example.chatserver.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    // 그룹 채팅방 개설
    @PostMapping("/room/group/create")
    public ResponseEntity<?> createGroupRoom(@RequestParam(value = "roomName") String roomName) {
        chatService.createGroupRoom(roomName);
        return ResponseEntity.ok().build();
    }

    // 그룹 채팅 목록 조회
    @GetMapping("/room/group/list")
    public ResponseEntity<?> getCroupChatRooms() {
        List<ChatRoomResponse> chatRooms = chatService.getGroupChatRooms();
        return new ResponseEntity<>(chatRooms, HttpStatus.OK);
    }

    // 그룹 채팅방 참여
    @PostMapping("/room/group/{roomId}/join")
    public ResponseEntity<?> joinGroupChatRoom(@PathVariable(value = "roomId") Long roomId) {
        chatService.addParticipantToGroupChat(roomId);
        return ResponseEntity.ok().build();
    }

    // 이전 메시지 조회
    @GetMapping("/history/{roomId}")
    public ResponseEntity<?> getChatHistory(@PathVariable(value = "roomId") Long roomId) {
        List<ChatHistoryResponse> chatHistoryResponses = chatService.getChatHistory(roomId);
        return new ResponseEntity<>(chatHistoryResponses, HttpStatus.OK);
    }

    // 채팅 메시지 읽음처리
    @PostMapping("/room/{roomId}/read")
    public ResponseEntity<?> messageRead(@PathVariable(value = "roomId") Long roomId) {
        chatService.messageRead(roomId);
        return ResponseEntity.ok().build();
    }
}
