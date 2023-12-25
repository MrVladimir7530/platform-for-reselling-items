package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

import java.io.IOException;
import java.security.Principal;

public interface UserService {
    boolean setPassword(NewPasswordDto newPasswordDto, Principal principal);
    UserDto getInfoUser(Principal principal);
    UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto, Principal principal);
    String updateAvatarUser(MultipartFile image, Principal principal) throws IOException;
}
