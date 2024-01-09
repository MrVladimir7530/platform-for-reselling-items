package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.PropertiesDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {
    AdMapper instance = Mappers.getMapper(AdMapper.class);
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final ImagesRepository imagesRepository;
    @Value("${path.to.avatars.folder}")
    private String avatarPath;
    @Override
    public AdEntity findById(Integer id) {
        log.info("Was invoked method for finding AdEntity by ID");
        return adRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public AdsDto getAllAds(Principal principal) {
        UserEntity user = getUser(principal);

        List<AdEntity> result = adRepository.findByUsersByAuthorId(user);
        AdsDto adsDto = new AdsDto();
        List<AdDto> adDtoList = new ArrayList<>();
        for (AdEntity adEntity : result) {
            AdDto adDto = instance.adEntityToAdDto(adEntity);
            adDtoList.add(adDto);
        }

        adsDto.setResults(adDtoList);
        adsDto.setCount(adDtoList.size());
        return adsDto;
    }

    @Override
    public AdDto addNewAd(PropertiesDto propertiesDto, MultipartFile image, Principal principal) throws IOException {
        UserEntity user = getUser(principal);

        ImageEntity imageEntity = new ImageEntity();

        String originalFilename = image.getOriginalFilename();
        Path path = Path.of(avatarPath, UUID.randomUUID() + "." + getExtension(originalFilename));

        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        readAndWriteInTheDirectory(image, path);

        imageEntity.setPath(path.toString());
        imageEntity.setContentType(image.getContentType());
        imageEntity.setSize(imageEntity.getSize());

        imagesRepository.save(imageEntity);
        log.info("The avatar is saved in repository");

        AdEntity adEntity = instance.propertiesDtoAndUserEntityAndStringImageToAdEntity(user, propertiesDto, imageEntity);
        adRepository.save(adEntity);

        return instance.adEntityToAdDto(adEntity);
    }

    @Override
    public ExtendedAdDto getInfoAboutAdd(Integer adId) {
        return null;
    }

    @Override
    public Boolean deleteAd(Integer adId) {
        AdEntity adEntity = adRepository.findById(adId)
                .orElseThrow(()->new NoSuchElementException());
        adRepository.delete(adEntity);
        return true;
    }

    @Override
    public AdDto updateInfoAboutAd(PropertiesDto propertiesDto) {
        return null;
    }

    @Override
    public AdsDto getAdAuthorizedUser() {
        return null;
    }

    private UserEntity getUser(Principal principal) {
        String name = principal.getName();
        return userRepository.findByUsername(name);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void readAndWriteInTheDirectory(MultipartFile fileImage, Path path) throws IOException {
        try (
                InputStream inputStream = fileImage.getInputStream();
                OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 4096);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 4096);
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }
    }

}
