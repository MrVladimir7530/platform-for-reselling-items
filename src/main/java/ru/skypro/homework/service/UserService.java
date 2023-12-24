package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

import java.security.Principal;

public interface UserService {
    boolean setPassword(NewPasswordDto newPasswordDto, Principal principal);
    UserDto getInfoUser();
    UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto);
    void updateAvatarUser(MultipartFile image);
}
