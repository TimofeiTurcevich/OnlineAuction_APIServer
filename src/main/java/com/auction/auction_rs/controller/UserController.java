package com.auction.auction_rs.controller;

import com.auction.auction_rs.components.JWTUtil;
import com.auction.auction_rs.entities.User;
import com.auction.auction_rs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @GetMapping
    public ResponseEntity<User> getUserById(@RequestParam Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateProfile(@RequestBody Map<String, String> mapObjects){
        System.out.println(mapObjects.get("id"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(mapObjects));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(true);
    }

    @GetMapping("/userId")
    public ResponseEntity<Integer> getUserId(@RequestHeader(HttpHeaders.AUTHORIZATION)String token){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jwtUtil.extractUserId(token));
    }
}
