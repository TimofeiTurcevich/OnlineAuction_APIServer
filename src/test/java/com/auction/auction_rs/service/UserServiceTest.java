package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.Authority;
import com.auction.auction_rs.entities.User;
import com.auction.auction_rs.entities.UserImage;
import com.auction.auction_rs.exceptions.EntityNotFoundException;
import com.auction.auction_rs.repositories.AuthorityRepository;
import com.auction.auction_rs.repositories.UserImageRepository;
import com.auction.auction_rs.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);

    private final UserImageRepository userImageRepository = mock(UserImageRepository.class);

    private final AuthorityRepository authorityRepository = mock(AuthorityRepository.class);

    private final UserService userService = new UserService(userRepository, userImageRepository, authorityRepository);

    private final User userResult = new User(
            1L,
            "Some nickname",
            "Some first name",
            "Some last name",
            "2024-02-02",
            "2024-02-02",
            "Some email",
            "Some password",
            new ArrayList<>(),
            new UserImage(1L,"Some filename", "Some filepath",null),
            null,
            null,
            null,
            null,
            null,
            null
    );

    @Test
    void getUserById(){
        when(userRepository.getUserById(1L))
                .thenReturn(Optional.of(userResult));

        assertEquals(userResult, userService.getUserById(1L));
    }

    @Test
    void getUserByWrongId(){
        when(userRepository.getUserById(2L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, ()-> userService.getUserById(2L));
    }

    @Test
    void registration() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.getOriginalFilename()).thenReturn("Some file name");

        when(authorityRepository.getAuthoritiesByName("USER")).thenReturn(new Authority(1L, "Some authority name", new HashSet<>()));

        doNothing().when(multipartFile).transferTo(new File("D:\\Users\\Timofei\\Desktop\\auction\\" +  "Some file name"));


        assertEquals(true, userService.registration(userResult, multipartFile));
    }

//    @Test
//    void delete(){
//
//    }
}
