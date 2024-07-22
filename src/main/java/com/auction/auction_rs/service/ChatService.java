package com.auction.auction_rs.service;

import com.auction.auction_rs.dto.ChatDTO;
import com.auction.auction_rs.dto.MessageDTO;
import com.auction.auction_rs.entities.Chat;
import com.auction.auction_rs.entities.Lot;
import com.auction.auction_rs.entities.Message;
import com.auction.auction_rs.entities.User;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.ChatRepository;
import com.auction.auction_rs.repositories.LotRepository;
import com.auction.auction_rs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final LotRepository lotRepository;

    public List<ChatDTO> getAllUserChats(Long id) {
        User tempt = userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + id + ") wasnt found"));
        List<ChatDTO> chatDTOS = new ArrayList<>();
        chatRepository.getAllByUserOrLot_Seller(tempt, tempt).forEach(i -> {
            chatDTOS.add(ChatDTO.builder()
                    .id(i.getId())
                    .lot(i.getLot())
                    .message(i.getMessage().size() > 0 ? i.getMessage().get(0) : null)
                    .user(i.getUser())
                    .fileName(i.getLot() != null ? (i.getLot().getLotImages().size() > 0 ? i.getLot().getLotImages().get(0).getFileName() : null) : null)
                    .build()
            );
        });

        return chatDTOS;
    }

    public Chat getDialog(Long id) {
        return chatRepository.getAllById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chat with such id(" + id + ") wasnt found"));
    }

    public Long getDialogIdOrCreate(Long userId, Long lotId) {
        Lot temptLot = lotRepository.getLotById(lotId)
                .orElseThrow(() -> new EntityNotFoundException("Lot with such id(" + lotId + ") wasnt found"));

        User temptUser = userRepository.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + userId + ") wasnt found"));

        Chat chat = chatRepository.getAllByUserAndLot(temptUser, temptLot).orElse(
                Chat.builder()
                        .user(temptUser)
                        .lot(temptLot)
                        .build()
        );
        chatRepository.save(chat);
        return chat.getId();
    }

    @Transactional
    public Chat sendMessage(MessageDTO messageDTO) {
        User temptUser = userRepository.getUserById(messageDTO.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + messageDTO.getSenderId() + ") wasnt found"));
        Chat chat = chatRepository.getAllById(messageDTO.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat with such id(" + messageDTO.getChatId() + ") wasnt found"));
        Message message = Message.builder()
                .message(messageDTO.getMessage())
                .sendDate(messageDTO.getSendDate())
                .chat(chat)
                .sender(temptUser)
                .build();
        temptUser.getMessage().add(message);
        chat.getMessage().add(message);
        return chat;
    }
}
