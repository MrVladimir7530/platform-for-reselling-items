package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
        return UserMapper.INSTANCE.toUserDto(user);
    }

    @Override
    public UpdateUserDto updateInfoUser(UpdateUserDto updateUserDto, Principal principal) {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name);
        UpdateUserDto oldUserDto = UserMapper.INSTANCE.toUpdateUserDto(user);
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        userRepository.save(user);
        return oldUserDto;
    }

    @Override
    public void updateAvatarUser(MultipartFile image, Principal principal) {

    }
}
