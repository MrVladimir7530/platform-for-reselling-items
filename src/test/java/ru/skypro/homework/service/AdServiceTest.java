package ru.skypro.homework.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdServiceTest {

    private AdRepository adRepositoryMock;
    private AdService out;
    private final AdEntity adEntityInit = new AdEntity();
    private final UserEntity user = new UserEntity();
    private final ImageEntity imageInit = new ImageEntity();

    @BeforeEach
    public void init() {
        adRepositoryMock = mock(AdRepository.class);
        out = new AdServiceImpl(adRepositoryMock);

        imageInit.setId(1);
        imageInit.setPath("Какой-то путь");
        imageInit.setSize(10L);
        imageInit.setContentType("Какой-то ContentType");

        user.setId(1);
        user.setUsername("UserTest");
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
    }

    @Test
    public void shouldCorrectResultFromMethodFindById() {
        when(adRepositoryMock.findById(anyInt())).thenReturn(Optional.of(adEntityInit));

        assertEquals(adEntityInit, out.findById(1));
        verify(adRepositoryMock, times(1)).findById(1);



    }

}
