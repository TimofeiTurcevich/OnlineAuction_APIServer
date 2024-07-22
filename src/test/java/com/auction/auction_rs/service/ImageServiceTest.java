package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.LotImage;
import com.auction.auction_rs.repositories.LotImageRepository;
import com.auction.auction_rs.repositories.UserImageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageServiceTest {
    private final LotImageRepository lotImageRepository = mock(LotImageRepository.class);

    private final UserImageRepository userImageRepository = mock(UserImageRepository.class);

    private final ImageService imageService = new ImageService(lotImageRepository, userImageRepository);

    private final byte[] someByte = {
            0x01, 0x23, 0x45, 0x67
    };

    private final LotImage lotImage = new LotImage(
            1L,
            "Some file name",
            "D:\\Users\\Timofei\\Desktop\\auction\\1717407341066.jpeg",
            null
    );

//    @Test
//    void getImage() throws IOException {
//        when(lotImageRepository.getAllByFileName("D:\\Users\\Timofei\\Desktop\\auction\\1717407341066.jpeg"))
//                .thenReturn(lotImage);
//
//        try(MockedStatic<Files> filesMockedStatic = Mockito.mockStatic(Files.class)){
//            filesMockedStatic.when(()->Files.readAllBytes(new File("D:\\Users\\Timofei\\Desktop\\auction\\1717407341066.jpeg").toPath())).thenReturn(someByte);
//        }
//
//        assertEquals(someByte, imageService.getImage("D:\\Users\\Timofei\\Desktop\\auction\\1717407341066.jpeg"));
//    }
}
