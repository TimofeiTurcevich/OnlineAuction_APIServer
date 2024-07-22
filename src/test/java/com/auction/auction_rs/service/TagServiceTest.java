package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.SubTag;
import com.auction.auction_rs.entities.Tag;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.SubTagRepository;
import com.auction.auction_rs.repositories.TagRepository;
import org.hibernate.action.internal.EntityActionVetoException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TagServiceTest {
    private final TagRepository tagRepository = mock(TagRepository.class);
    private final SubTagRepository subTagRepository = mock(SubTagRepository.class);

    private final Set<Tag> tagsResult = Set.of(
            new Tag(1L, "Some tag title", "Some file name", null),
            new Tag(2L, "Some tag title2", "Some file name", null)
    );

    private final Set<SubTag> subTagsResult = Set.of(
            new SubTag(1L, "Some subtag title", "Some filename", null, null),
            new SubTag(1L, "Some subtag title", "Some filename", null, null),
            new SubTag(1L, "Some subtag title", "Some filename", null, null)
    );

    private final Tag tagResult = new Tag(1L, "Some tag title", "Some file name", subTagsResult);

    private final TagService tagService = new TagService(tagRepository, subTagRepository);

    @Test
    void getAllTags(){
        when(tagRepository.getAllBy())
                .thenReturn(tagsResult);

        assertEquals(tagsResult, tagService.getAllTags());
    }

    @Test
    void getAllSubTagsByTag(){
        subTagsResult.forEach(i->i.setTag(tagResult));

        when(tagRepository.getTagById(1L))
                .thenReturn(Optional.of(tagResult));

        assertEquals(subTagsResult, tagService.getAllSubTags(1L));
    }

    @Test
    void getAllSubTagByTagWithWrongId(){
        when(tagRepository.getTagById(2L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()->tagService.getAllSubTags(2L));
    }

    @Test
    void getAllSubTags(){
        when(subTagRepository.getAllBy())
                .thenReturn(subTagsResult);

        assertEquals(subTagsResult, tagService.getAllSubTags());
    }
}
