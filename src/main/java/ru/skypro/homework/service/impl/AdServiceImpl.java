package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {
    AdMapper instance = Mappers.getMapper(AdMapper.class);
    private final UserRepository userRepository;
    private final AdRepository adRepository;
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
    public AdDto addNewAd(MultipartFile multipartFile, MultipartFile image) {
        return null;
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
    public AdDto updateInfoAboutAd(CreateOrUpdateAdDto createOrUpdateAdDto) {
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
}
