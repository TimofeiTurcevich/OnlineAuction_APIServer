package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.SubTag;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.SubTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubTagService {
    private final SubTagRepository subTagRepository;

    public SubTag getSubTagById(Long id){
        return subTagRepository.getAllById(id)
                .orElseThrow(()-> new EntityNotFoundException("SubTag with such id(" + id + ") wasnt found"));
    }
}
