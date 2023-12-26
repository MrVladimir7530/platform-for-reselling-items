package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.PropertiesDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AdMapperTest {
    @Autowired
    private AdMapper adMapper;

    private final UserEntity user = new UserEntity();
    private final AdDto adDto = new AdDto();

    private final PropertiesDto propertiesDto = new PropertiesDto();

    @BeforeEach
    public void init() {
        user.setId(1);
        user.setUsername("UserTest");
        user.setFirstName("User");
        user.setLastName("Test");
        user.setPassword("password");
        user.setPhone("phone");
        user.setRole(RoleDto.USER.toString());
        user.setImage("image");
        user.setEmail("email");

        adDto.setAuthor(1);
        adDto.setImage("image");
        adDto.setPk(1);
        adDto.setPrice(20);
        adDto.setTitle("title");

        propertiesDto.setTitle("title");
        propertiesDto.setPrice(30);
        propertiesDto.setDescription("Description");



    }

    @Test
    public void shouldCorrectResultFromMethodAdDtoAndUserEntityToAdEntity() {


        AdEntity adEntity = adMapper.adDtoAndUserEntityToAdEntity(user, adDto);

        assertNotNull(adEntity);
        assertEquals(adDto.getPk(), adEntity.getPk());
        assertEquals(adDto.getImage(), adEntity.getImage());
        assertEquals(adDto.getPrice(), adEntity.getPrice());
        assertEquals(adDto.getTitle(), adEntity.getTitle());
        assertEquals(adDto.getAuthor(), adEntity.getUsersByAuthorId().getId());


    }
    @Test
    public void shouldCorrectResultFromMethodPropertiesDtoAndUserEntityAndStringImageToAdEntity() {


        AdEntity adEntity = adMapper.propertiesDtoAndUserEntityAndStringImageToAdEntity(user
                , propertiesDto, "image");

        assertNotNull(adEntity);
        assertEquals("image", adEntity.getImage());
        assertEquals(propertiesDto.getPrice(), adEntity.getPrice());
        assertEquals(propertiesDto.getTitle(), adEntity.getTitle());
        assertEquals(propertiesDto.getDescription(), adEntity.getDescription());
        assertEquals(adDto.getAuthor(), adEntity.getUsersByAuthorId().getId());

    }

}
