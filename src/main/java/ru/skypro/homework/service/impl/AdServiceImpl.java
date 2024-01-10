package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
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
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.security.Principal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {
    AdMapper instance = Mappers.getMapper(AdMapper.class);
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final ImageService imageService;


    @Override
    public AdEntity findById(Integer id) {
        log.info("Was invoked method for finding AdEntity by ID");
        return adRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public AdsDto getMyAds(Principal principal) {
        UserEntity user = getUser(principal);

        List<AdEntity> result = adRepository.findByUsersByAuthorId(user);
        AdsDto adsDto = new AdsDto();
        List<AdDto> adDtoList = new ArrayList<>();
        for (AdEntity adEntity : result) {
            AdDto adDto = instance.adEntityToAdDto(adEntity);
            adDto.setImage("/" + adEntity.getImageEntity().getPath());
            adDtoList.add(adDto);
        }

        adsDto.setResults(adDtoList);
        adsDto.setCount(adDtoList.size());
        return adsDto;
    }

    @Override
    public AdsDto getAllAds() {
        List<AdEntity> result = adRepository.findAll();
        AdsDto adsDto = new AdsDto();
        List<AdDto> adDtoList = new ArrayList<>();
        for (AdEntity adEntity : result) {
            AdDto adDto = instance.adEntityToAdDto(adEntity);
            adDto.setImage("/" + adEntity.getImageEntity().getPath());
            adDtoList.add(adDto);
        }

        adsDto.setResults(adDtoList);
        adsDto.setCount(adDtoList.size());
        return adsDto;
    }

    @Override
    public AdDto addNewAd(PropertiesDto propertiesDto, MultipartFile image, Principal principal) throws IOException {
        UserEntity user = getUser(principal);

        ImageEntity imageEntity = imageService.saveImage(image);

        log.info("The avatar is saved in repository");
        AdEntity adEntity = instance.propertiesDtoAndUserEntityAndStringImageToAdEntity(user, propertiesDto, imageEntity);
        adRepository.save(adEntity);

        return instance.adEntityToAdDto(adEntity);
    }

    @Override
    public ExtendedAdDto getInfoAboutAd(Integer id) {
        AdEntity adEntity = findById(id);
        UserEntity user = adEntity.getUsersByAuthorId();
        ExtendedAdDto extendedAdDto = instance.adEntityAndUserEntityToExtendedAdDto(user, adEntity);
        extendedAdDto.setImage("/" + adEntity.getImageEntity().getPath());
        return extendedAdDto;
    }

    @Override
    public Boolean deleteAd(Integer adId) {
        AdEntity adEntity = adRepository.findById(adId)
                .orElseThrow(() -> new NoSuchElementException());
        adRepository.delete(adEntity);
        return true;
    }

    @Override
    public AdDto editAd(Integer id, PropertiesDto propertiesDto) {
        AdEntity adEntity = findById(id);
        adEntity.setTitle(propertiesDto.getTitle());
        adEntity.setPrice(propertiesDto.getPrice());
        adEntity.setDescription(propertiesDto.getDescription());
        adRepository.save(adEntity);

        AdDto adDto = instance.adEntityToAdDto(adEntity);
        adDto.setImage("/"+ adEntity.getImageEntity().getPath());
        return adDto;
    }

    @Override
    public byte[] updateImageInAd(MultipartFile image, Integer adId) throws IOException {
        AdEntity adEntity = adRepository.findById(adId).get();

        ImageEntity imageEntity = adEntity.getImageEntity();
        imageService.updateImage(image, imageEntity.getId());

        return image.getBytes();
    }

    private UserEntity getUser(Principal principal) {
        String name = principal.getName();
        return userRepository.findByUsername(name);
    }
}
