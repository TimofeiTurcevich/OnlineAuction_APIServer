package com.auction.auction_rs.controller;

import com.auction.auction_rs.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class Test {
    private final ImageService imageService;


    @GetMapping
    public ResponseEntity<byte[]> test() throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getImage("photo_2023-10-14_21-07-34.jpg"));
    }

    @GetMapping("/success")
    public ResponseEntity<String> testSuccess(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Hoorey!");
    }
}
