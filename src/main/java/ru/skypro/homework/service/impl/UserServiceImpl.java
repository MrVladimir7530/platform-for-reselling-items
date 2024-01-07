package ru.skypro.homework.service.impl;

import liquibase.pro.packaged.B;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.AppUserDetailsManager;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.ImagesRepository;
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
    private final ImagesRepository imagesRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void setPassword(NewPasswordDto newPasswordDto) {
        String currentPassword = newPasswordDto.getCurrentPassword();
        String newPassword = newPasswordDto.getNewPassword();

        changePassword(currentPassword, newPassword);

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

        ImageEntity image = user.getImageEntity();
        boolean isEmptyImage = image == null;
        if (isEmptyImage) {
            image = new ImageEntity();
        }

        String originalFilename = fileImage.getOriginalFilename();
        Path path = Path.of(avatarPath, UUID.randomUUID() + "." + getExtension(originalFilename));

        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        readAndWriteInTheDirectory(fileImage, path);

        image.setPath(path.toString());
        image.setContentType(fileImage.getContentType());
        image.setSize(fileImage.getSize());

        imagesRepository.save(image);

        if (isEmptyImage) {
            user.setImageEntity(image);
            userRepository.save(user);
        }
        log.info("The avatar is saved in repository");
        return path.toString();
    }

    private void readAndWriteInTheDirectory(MultipartFile fileImage, Path path) throws IOException {
        try (
                InputStream inputStream = fileImage.getInputStream();
                OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 4096);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 4096);
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
