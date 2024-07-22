package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.SubTag;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.SubTagRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubTagServiceTest {
    private final SubTagRepository subTagRepository = mock(SubTagRepository.class);

    private final SubTagService subTagService = new SubTagService(subTagRepository);

    private final SubTag subTag = new SubTag(1L, "Some subtag title", "Some filename", null, null);

    @Test
    void getSubTagById(){
        when(subTagRepository.getAllById(1L))
                .thenReturn(Optional.of(subTag));

        assertEquals(subTag, subTagService.getSubTagById(1L));
    }

    @Test
    void getSubTagByWrongId(){
        when(subTagRepository.getAllById(2L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> subTagService.getSubTagById(2L));
    }
}
