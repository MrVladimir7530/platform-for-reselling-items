package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.dto.PropertiesDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.initialization.DtoInitialization;
import ru.skypro.homework.initialization.EntityInitialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AdMapperTest {
    @Autowired
    private AdMapper adMapper;

    private UserEntity user;
    private AdDto adDtoInit;
    private PropertiesDto propertiesDto;
    private AdEntity adEntityInit;
    private ImageEntity imageInit;

    @BeforeEach
    public void init() {
        imageInit = EntityInitialization.getImageEntity();
        user = EntityInitialization.getUserEntity();
        adEntityInit = EntityInitialization.getAdEntity();
        adDtoInit = DtoInitialization.getAdDto();
        propertiesDto = DtoInitialization.getPropertiesDto();

    }

    @Test
    public void shouldCorrectResultFromMethodAdDtoAndUserEntityToAdEntity() {


        AdEntity adEntity = adMapper.adDtoAndUserEntityToAdEntity(user, adDtoInit, imageInit);

        assertNotNull(adEntity);
        assertEquals(adDtoInit.getPk(), adEntity.getPk());
        assertEquals(adDtoInit.getImage(), adEntity.getImageEntity().getPath());
        assertEquals(adDtoInit.getPrice(), adEntity.getPrice());
        assertEquals(adDtoInit.getTitle(), adEntity.getTitle());
        assertEquals(adDtoInit.getAuthor(), adEntity.getUsersByAuthorId().getId());


    }
    @Test
    public void shouldCorrectResultFromMethodPropertiesDtoAndUserEntityAndStringImageToAdEntity() {


        AdEntity adEntity = adMapper.propertiesDtoAndUserEntityAndStringImageToAdEntity(user
                , propertiesDto, imageInit);

        assertNotNull(adEntity);
        assertEquals(imageInit.getPath(), adEntity.getImageEntity().getPath());
        assertEquals(propertiesDto.getPrice(), adEntity.getPrice());
        assertEquals(propertiesDto.getTitle(), adEntity.getTitle());
        assertEquals(propertiesDto.getDescription(), adEntity.getDescription());
        assertEquals(adDtoInit.getAuthor(), adEntity.getUsersByAuthorId().getId());

    }
    @Test
    public void shouldCorrectResultFromMethodAdEntityAndUserEntityToExtendedAdDto() {


        ExtendedAdDto extendedAdDto = adMapper.adEntityAndUserEntityToExtendedAdDto(user
                , adEntityInit);

        assertNotNull(extendedAdDto);
        assertEquals(adEntityInit.getPk(), extendedAdDto.getPk());
        assertEquals(user.getFirstName(), extendedAdDto.getAuthorFirstName());
        assertEquals(user.getLastName(), extendedAdDto.getAuthorLastName());
        assertEquals(adEntityInit.getDescription(), extendedAdDto.getDescription());
        assertEquals(user.getEmail(), extendedAdDto.getEmail());
        assertEquals(adEntityInit.getImageEntity().getPath(), extendedAdDto.getImage());
        assertEquals(user.getPhone(), extendedAdDto.getPhone());
        assertEquals(adEntityInit.getPrice(), extendedAdDto.getPrice());
        assertEquals(adEntityInit.getTitle(), extendedAdDto.getTitle());

    }
    @Test
    public void shouldCorrectResultFromMethodAdEntityToAdDto() {


        AdDto  adDto= adMapper.adEntityToAdDto(adEntityInit);

        assertNotNull(adDto);
        assertEquals(adEntityInit.getUsersByAuthorId().getId(), adDto.getAuthor());
        assertEquals(adEntityInit.getImageEntity().getPath(), adDto.getImage());
        assertEquals(adEntityInit.getPk(), adDto.getPk());
        assertEquals(adEntityInit.getPrice(), adDto.getPrice());
        assertEquals(adEntityInit.getTitle(), adDto.getTitle());


    }

}
