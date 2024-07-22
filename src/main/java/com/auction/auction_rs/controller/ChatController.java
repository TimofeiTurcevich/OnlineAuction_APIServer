package com.auction.auction_rs.controller;

import com.auction.auction_rs.dto.ChatDTO;
import com.auction.auction_rs.dto.MessageDTO;
import com.auction.auction_rs.entities.Chat;
import com.auction.auction_rs.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAllUserChat(@RequestParam Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(chatService.getAllUserChats(id));
    }

    @GetMapping("/dialog")
    public ResponseEntity<Chat> getDialog(@RequestParam Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(chatService.getDialog(id));
    }

    @GetMapping("/getDialogId")
    public ResponseEntity<Long> getDialogId(@RequestParam Long userId, @RequestParam Long lotId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(chatService.getDialogIdOrCreate(userId,lotId));
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<Chat> sendMessage(@RequestBody MessageDTO messageDTO){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(chatService.sendMessage(messageDTO));
    }
}
