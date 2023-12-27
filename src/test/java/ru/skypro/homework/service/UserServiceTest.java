package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTest {
    private ImagesRepository imagesRepository;
    private UserRepository userRepository;
    private UserService userService;
    private MultipartFile multipartFile;
    Principal principal;
    Integer id = 1;
    String username = "vladimir@mail";
    String firstName = "vladimir";
    String lastName = "volkov";
    String password = "1234";
    String phone = "111";

    @BeforeEach
    public void init() {
        imagesRepository = Mockito.mock(ImagesRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        multipartFile = Mockito.mock(MultipartFile.class);
        userService = new UserServiceImpl(userRepository, imagesRepository);
        principal = () -> username;
    }

    @Test
    public void setPasswordTestOnTrue() {
        when(userRepository.findByUsername(anyString())).thenReturn(getUser());
        when(userRepository.save(any(UserEntity.class))).thenReturn(getUser());

        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setCurrentPassword(password);
        newPasswordDto.setNewPassword(password + password);

        boolean result = userService.setPassword(newPasswordDto, principal);
        assertTrue(result);
    }

    @Test
    public void setPasswordTestOnFalse() {
        when(userRepository.findByUsername(anyString())).thenReturn(getUser());
        when(userRepository.save(any(UserEntity.class))).thenReturn(getUser());

        NewPasswordDto newPasswordDto = new NewPasswordDto();
        newPasswordDto.setCurrentPassword(password + "1");
        newPasswordDto.setNewPassword(password + password);

        boolean result = userService.setPassword(newPasswordDto, principal);
        assertFalse(result);
    }

    @Test
    public void getInfoUserTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(getUser());

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(id);
        expectedUserDto.setFirstName(firstName);
        expectedUserDto.setLastName(lastName);
        expectedUserDto.setPhone(phone);

        UserDto resultUserDto = userService.getInfoUser(principal);

        assertEquals(expectedUserDto, resultUserDto);
    }

    @Test
    public void updateInfoUserTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(getUser());

        UpdateUserDto userDto = new UpdateUserDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setPhone(phone);

        UpdateUserDto userDtoResult = userService.updateInfoUser(new UpdateUserDto(), principal);

        assertEquals(userDto, userDtoResult);
    }


//    @Test
//    public void updateAvatarUserTest() throws IOException {
//        when(userRepository.findByUsername(anyString())).thenReturn(getUser());
//        when(multipartFile.getOriginalFilename()).thenReturn(".jpg");
//        when(userRepository.save(any(UserEntity.class))).thenReturn(getUser());
//        mockStatic(UUID.class);
//        when(multipartFile.getInputStream()).thenReturn(new InputStream() {
//
//            @Override
//            public int read() throws IOException {
//                return -1;
//            }
//        });
//
//        ReflectionTestUtils.setField(userService, "avatarPath", "C:\\avatar");
//
//        String s = userService.updateAvatarUser(multipartFile, principal);

//    }



    private UserEntity getUser() {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        return user;
    }
}
