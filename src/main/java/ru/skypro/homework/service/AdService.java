package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.PropertiesDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.AdEntity;

import java.io.IOException;
import java.security.Principal;

public interface AdService {

    AdEntity findById(Integer id);

    AdsDto getMyAds(Principal principal);
    AdsDto getAllAds();

    AdDto addNewAd(PropertiesDto propertiesDto, MultipartFile image, Principal principal) throws IOException;

    ExtendedAdDto getInfoAboutAd(Integer adId);

    Boolean deleteAd(Integer adId);

    AdDto editAd(Integer id, PropertiesDto propertiesDto);

    byte[] updateImageInAd(MultipartFile image, Integer adId) throws IOException;
}
