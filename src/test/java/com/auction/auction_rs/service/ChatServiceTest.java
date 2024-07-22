package com.auction.auction_rs.service;

import com.auction.auction_rs.dto.ChatDTO;
import com.auction.auction_rs.dto.MessageDTO;
import com.auction.auction_rs.entities.*;
import com.auction.auction_rs.repositories.ChatRepository;
import com.auction.auction_rs.repositories.LotRepository;
import com.auction.auction_rs.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChatServiceTest {

    private final ChatRepository chatRepository = mock(ChatRepository.class);

    private final UserRepository userRepository = mock(UserRepository.class);

    private final LotRepository lotRepository = mock(LotRepository.class);

    private final ChatService chatService = new ChatService(chatRepository, userRepository, lotRepository);

    private final List<Chat> chatsResult = new ArrayList<>(List.of(
            new Chat(1L, null, null, new ArrayList<>()),
            new Chat(1L, null, null, new ArrayList<>())
    ));

    private final User user = new User(
            1L,
            "Some nickname",
            "Some first name",
            "Some last name",
            "2024-02-02",
            "2024-02-02",
            "Some email",
            "Some password",
            new ArrayList<>(),
            new UserImage(1L,"Some filename", "Some filepath",null),
            null,
            null,
            null,
            chatsResult,
            new ArrayList<>(),
            new ArrayList<>()
    );

    private final List<ChatDTO> chatDTOSResult = new ArrayList<>(List.of(
            new ChatDTO(1L, null, null, null, null),
            new ChatDTO(1L, null, null, null, null)
    ));

    @Test
    void getALlChats(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        when(chatRepository.getAllByUserOrLot_Seller(user, user))
                .thenReturn(chatsResult);

        assertEquals(chatDTOSResult, chatService.getAllUserChats(1L));
    }

    @Test
    void getDialog(){
        when(chatRepository.getAllById(1L))
                .thenReturn(Optional.of(chatsResult.get(0)));

        assertEquals(chatsResult.get(0), chatService.getDialog(1L));
    }

    @Test
    void getDialogOrCreate(){
        Lot tempt = mock(Lot.class);

        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        when(lotRepository.getLotById(1L))
                .thenReturn(Optional.of(tempt));

        when(chatRepository.getAllByUserAndLot(user, tempt))
                .thenReturn(Optional.of(chatsResult.get(0)));

        assertEquals(chatsResult.get(0).getId(), chatService.getDialogIdOrCreate(1L, 1L));
    }

    @Test
    void sendMessage(){
        MessageDTO messageDTO = MessageDTO.builder()
                .message("Some message")
                .chatId(1L)
                .sendDate("2024-02-02")
                .senderId(1L)
                .build();

        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(user));

        when(chatRepository.getAllById(1L))
                .thenReturn(Optional.of(chatsResult.get(0)));

        assertEquals(chatsResult.get(0), chatService.sendMessage(messageDTO));
    }
}
