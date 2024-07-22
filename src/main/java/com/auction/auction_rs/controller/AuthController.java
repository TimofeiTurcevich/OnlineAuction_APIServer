package com.auction.auction_rs.controller;

import com.auction.auction_rs.dto.LoginDTO;
import com.auction.auction_rs.entities.User;
import com.auction.auction_rs.service.UserService;
import com.auction.auction_rs.components.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userService.loadUserByUsername(loginDTO.getLogin());

        String jwt = jwtUtil.generateToken(userDetails.getUsername(),  userDetails.getAuthorities());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jwt);
    }

    @PostMapping("/registration")
    public ResponseEntity<Boolean> registration(@RequestParam User user, @RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.registration(user, file));
    }

}
