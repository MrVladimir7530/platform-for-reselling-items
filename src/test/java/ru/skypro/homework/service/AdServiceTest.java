package ru.skypro.homework.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdServiceTest {

    private AdRepository adRepositoryMock;
    private UserRepository userRepositoryMock;
    private ImagesRepository imagesRepositoryMock;
    private AdService adService;
    private final AdEntity adEntityInit = new AdEntity();
    private final UserEntity user = new UserEntity();
    private final ImageEntity imageInit = new ImageEntity();
    Principal principal;
    private String username = "UserTest";
    AdMapper instance = Mappers.getMapper(AdMapper.class);


    @BeforeEach
    public void init() {
        adRepositoryMock = mock(AdRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        imagesRepositoryMock = mock(ImagesRepository.class);
        adService = new AdServiceImpl(userRepositoryMock, adRepositoryMock, imagesRepositoryMock);

        imageInit.setId(1);
        imageInit.setPath("Какой-то путь");
        imageInit.setSize(10L);
        imageInit.setContentType("Какой-то ContentType");

        user.setId(1);
        user.setUsername(username);
        user.setFirstName("User");
        user.setLastName("Test");
        user.setPassword("password");
        user.setPhone("phone");
        user.setRole(Role.USER);
        user.setImageEntity(imageInit);
        user.setEmail("email");

        adEntityInit.setPk(1);
        adEntityInit.setPrice(30);
        adEntityInit.setImageEntity(imageInit);
        adEntityInit.setTitle("title");
        adEntityInit.setDescription("Description");
        adEntityInit.setUsersByAuthorId(user);
        principal = () -> username;
    }

    @Test
    public void shouldCorrectResultFromMethodFindById() {
        when(adRepositoryMock.findById(anyInt())).thenReturn(Optional.of(adEntityInit));

        assertEquals(adEntityInit, adService.findById(1));
        verify(adRepositoryMock, times(1)).findById(1);

    }

    @Test
    public void getMyAdsTest() {
        List<AdEntity> result = new ArrayList<>();
        result.add(adEntityInit);
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(user);
        when(adRepositoryMock.findByUsersByAuthorId(any(UserEntity.class))).thenReturn(result);

        AdsDto myAds = adService.getMyAds(principal);

        AdsDto adsDto = new AdsDto();
        List<AdDto> adDtoList = new ArrayList<>();
        AdDto adDto = instance.adEntityToAdDto(adEntityInit);
        adDtoList.add(adDto);
        adsDto.setResults(adDtoList);
        adsDto.setCount(adDtoList.size());
        assertEquals(adsDto, myAds);
    }

}
