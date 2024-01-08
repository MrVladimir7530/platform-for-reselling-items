package ru.skypro.homework.service;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.AdEntity;

import java.security.Principal;

public interface AdService {

    AdEntity findById(Integer id);

    AdsDto getAllAds(Principal principal);

    AdDto addNewAd(MultipartFile multipartFile, MultipartFile image);

    ExtendedAdDto getInfoAboutAdd(Integer adId);

    Boolean deleteAd(Integer adId);

    AdDto updateInfoAboutAd(CreateOrUpdateAdDto createOrUpdateAdDto);

    AdsDto getAdAuthorizedUser();

    //todo дописать обновление картинки
}
