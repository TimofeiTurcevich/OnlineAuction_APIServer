package com.auction.auction_rs.service;

import com.auction.auction_rs.entities.LotImage;
import com.auction.auction_rs.entities.UserImage;
import com.auction.auction_rs.repositories.LotImageRepository;
import com.auction.auction_rs.repositories.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final LotImageRepository imageRepository;
    private final UserImageRepository userImageRepository;
    private final String FOLDER_PATH = "D:\\Users\\Timofei\\Desktop\\auction\\";

    public byte[] getImage(String fileName) throws IOException {
        LotImage image = imageRepository.getAllByFileName(fileName);
        if(image==null){
            return new byte[]{};
        }
        return Files.readAllBytes(new File(image.getFilePath()).toPath());
    }

    public byte[] getUserImage(String fileName) throws IOException {
        UserImage image = userImageRepository.getAllByFileName(fileName);
        return Files.readAllBytes(new File(image.getFilePath()).toPath());
    }

    public byte[] getTagImage(String fileName) throws IOException {
        return Files.readAllBytes(new File(FOLDER_PATH + fileName).toPath());
    }

    public Boolean uploadImage(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH+file.getOriginalFilename();

        LotImage image = imageRepository.save(
                LotImage.builder()
                        .fileName(file.getOriginalFilename())
                        .filePath(filePath)
                        .build()
        );

        file.transferTo(new File(filePath));

        return true;
    }

    public Boolean uploadImage(List<MultipartFile> file) throws IOException {
        file.forEach(i->{
            String filePath = FOLDER_PATH+i.getOriginalFilename();

            LotImage image = imageRepository.save(
                    LotImage.builder()
                            .fileName(i.getOriginalFilename())
                            .filePath(filePath)
                            .build()
            );

            try {
                i.transferTo(new File(filePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        return true;
    }
}
