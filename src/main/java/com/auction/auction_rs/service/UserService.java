package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.Authority;
import com.auction.auction_rs.entities.LotImage;
import com.auction.auction_rs.entities.User;
import com.auction.auction_rs.entities.UserImage;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.AuthorityRepository;
import com.auction.auction_rs.repositories.UserImageRepository;
import com.auction.auction_rs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserImageRepository imageRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    private final String FOLDER_PATH = "D:\\Users\\Timofei\\Desktop\\auction\\";

    public User getUserById(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + id + ") wasnt found"));
    }

    @Transactional
    public Boolean registration(User user, MultipartFile file) throws IOException {
        Authority tempt = authorityRepository.getAuthoritiesByName("USER");

        String filePath = FOLDER_PATH + file.getOriginalFilename();


        file.transferTo(new File(filePath));

        user.setRegistrationDate(String.valueOf(Timestamp.from(Instant.now())));
        user.setEmail("Some email1@some.com");

        userRepository.save(user);

        user.addToAuthority(tempt);

        user.setUserImage(
                UserImage.builder()
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath)
                        .user(user)
                        .build()
        );


        return true;
    }

    @Transactional
    public User updateUser(Map<String, String> mapObject){
        User user =  userRepository.getUserById(Long.valueOf(mapObject.get("id")))
                .orElseThrow(() -> new EntityNotFoundException("User with such id(" + mapObject.get("id") + ") wasnt found"));

        user.setFirstName(mapObject.get("firstName"));
        user.setLastName(mapObject.get("lastName"));

        if(mapObject.get("oldPass") != null && mapObject.get("newPass") != null && user.getPassword().equals(passwordEncoder.encode(mapObject.get("oldPass")))){
            user.setPassword(passwordEncoder.encode(mapObject.get("newPass")));
        }

        user.setDateOfBirth(mapObject.get("birthDate"));

        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByNickname(username);

        return new org.springframework.security.core.userdetails.User(user.getNickname(), user.getPassword(), user.getAuthorities());
    }
}
