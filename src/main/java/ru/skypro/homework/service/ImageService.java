package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.ImageEntity;

import java.io.IOException;
import java.security.Principal;

public interface ImageService {
    ImageEntity saveImage(MultipartFile imageFile) throws IOException;

    ImageEntity getImage(Integer imageId);

    ImageEntity updateImage(MultipartFile image, Integer imageId) throws IOException;

}
