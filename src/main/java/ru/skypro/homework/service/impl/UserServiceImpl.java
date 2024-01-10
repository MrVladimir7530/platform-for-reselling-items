package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.security.Principal;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserMapper instance = Mappers.getMapper(UserMapper.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Override
    public boolean setPassword(NewPasswordDto newPasswordDto) {
        String currentPassword = newPasswordDto.getCurrentPassword();
        String newPassword = newPasswordDto.getNewPassword();

        try {
            changePassword(currentPassword, newPassword);
        } catch (NullPointerException e) {
            log.error("Error: %s", e);
            return false;
        }
        log.info("password changed");
        return true;
    }

    private void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String name = currentUser.getName();
        UserEntity user = userRepository.findByUsername(name);

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            String encodeNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodeNewPassword);

            userRepository.save(user);
        }
    }

    @Override
    public UserDto getInfoUser(Principal principal) {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);
        log.info("information received successfully");
        UserDto userDto = instance.toUserDto(user);
        ImageEntity imageEntity = user.getImageEntity();
        if (imageEntity != null) {
            userDto.setImage("/" + imageEntity.getPath());
        }
        return userDto;
    }

    @Override
    public UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto, Principal principal) {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);

        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());

        log.info("User information has been changed");
        userRepository.save(user);
        return updateUserDto;
    }

    @Override
    public String updateAvatarUser(MultipartFile fileImage, Principal principal) throws IOException {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);

        ImageEntity image = user.getImageEntity();
        ImageEntity imageEntity;

        if (image == null) {
            imageEntity = imageService.saveImage(fileImage);
        } else {
            imageEntity = imageService.updateImage(fileImage, image.getId());
        }

        user.setImageEntity(imageEntity);
        userRepository.save(user);

        log.info("The avatar is saved in repository");
        return imageEntity.getPath();
    }

}
