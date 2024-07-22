package com.auction.auction_rs.controller;

import com.auction.auction_rs.dto.TestDTO;
import com.auction.auction_rs.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getImage(fileName));
    }

    @GetMapping("/userImage/{fileName}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable String fileName) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getUserImage(fileName));
    }

    @GetMapping("/tagImage/{fileName}")
    public ResponseEntity<byte[]> getTagImage(@PathVariable String fileName) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getTagImage(fileName));
    }

    @PostMapping
    public ResponseEntity<Boolean> uploadImage( @RequestParam("image") List<MultipartFile> file, @RequestParam("name") String name) throws IOException {
        System.out.println(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageService.uploadImage(file));
    }
}
