package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserMapper instance = Mappers.getMapper(UserMapper.class);
    @Value("${path.to.avatars.folder}")
    private String avatarPath;
    private final UserRepository userRepository;

    @Override
    public boolean setPassword(NewPasswordDto newPasswordDto, Principal principal) {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);
        if (newPasswordDto.getCurrentPassword().equals(user.getPassword())) {
            user.setPassword(newPasswordDto.getNewPassword());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDto getInfoUser(Principal principal) {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);
        return instance.toUserDto(user);
    }

    @Override
    public UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto, Principal principal) {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);
        UpdateUserDto oldUserDto = instance.toUpdateUserDto(user);
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        log.info("User information has been changed");
        userRepository.save(user);
        return oldUserDto;
    }

    @Override
    public String updateAvatarUser(MultipartFile fileImage, Principal principal) throws IOException {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);

        Path path = Path.of(avatarPath, UUID.randomUUID() + "." + getExtension(fileImage.getOriginalFilename()));

        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);
        try (
                InputStream inputStream = fileImage.getInputStream();
                OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 4096);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 4096);
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }
        user.setImage(path.toString());
        userRepository.save(user);
        log.info("The avatar is saved in repository");
        return path.toString();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
