package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.SubTag;
import com.auction.auction_rs.entities.Tag;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.SubTagRepository;
import com.auction.auction_rs.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final SubTagRepository subTagRepository;

    public Set<Tag> getAllTags() {
//        tagRepository.getAllBy().forEach(i->System.out.println(i.getSubTags()));
        return tagRepository.getAllBy();
    }

    public Set<SubTag> getAllSubTags(Long id) {
        Tag tempt = tagRepository.getTagById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag with such id(" + id + ") wasnt found"));
        return tempt.getSubTags();
    }

    public Set<SubTag> getAllSubTags() {
        return subTagRepository.getAllBy();
    }

}
