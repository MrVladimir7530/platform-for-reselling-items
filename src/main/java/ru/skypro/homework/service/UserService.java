package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

import java.io.IOException;
import java.security.Principal;

public interface UserService {
    /**
     * Метод позволяющий изменять пароль авторизированного пользователя
     */
    boolean setPassword(NewPasswordDto newPasswordDto);
    /**
     * Данный метод дает информацию об авторизированного пользователе
     */
    UserDto getInfoUser(Principal principal);
    /**
     * Метод, который позволяет изменить имя, фамилию или номер телефона авторизированного пользователя
     */
    UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto, Principal principal);
    /**
     * Метод, который позволяет изменить имя, фамилию или номер телефона авторизированного пользователя
     */
    String updateAvatarUser(MultipartFile image, Principal principal) throws IOException;
}
