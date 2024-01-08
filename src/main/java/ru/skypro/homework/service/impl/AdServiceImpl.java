package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    @Override
    public AdEntity findById(Integer id) {
        log.info("Was invoked method for finding AdEntity by ID");
        return adRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public AdsDto getAllAds() {
        return null;
    }

    @Override
    public AdDto addNewAd(MultipartFile multipartFile, MultipartFile image) {
        return null;
    }

    @Override
    public ExtendedAdDto getInfoAboutAdd(Integer adId) {
        return null;
    }

    @Override
    public Boolean deleteAd(Integer adId) {
        return null;
    }

    @Override
    public AdDto updateInfoAboutAd(CreateOrUpdateAdDto createOrUpdateAdDto) {
        return null;
    }

    @Override
    public AdsDto getAdAuthorizedUser() {
        return null;
    }
}
