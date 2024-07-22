package com.auction.auction_rs.controller;

import com.auction.auction_rs.entities.SubTag;
import com.auction.auction_rs.entities.Tag;
import com.auction.auction_rs.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Set<Tag>> getAllTags(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagService.getAllTags());
    }

    @GetMapping("/subTags")
    public ResponseEntity<Set<SubTag>> getSubTagsById(@RequestParam Long tagId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagService.getAllSubTags(tagId));
    }

    @GetMapping("/allSubTags")
    public ResponseEntity<Set<SubTag>> getAllSubTags(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagService.getAllSubTags());
    }
}
