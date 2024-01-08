package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    UserMapper instance = Mappers.getMapper(UserMapper.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public boolean login(String userName, String password) {
        UserEntity user = userRepository.findByUsername(userName);
        if (user == null) {
            log.info("user is not exists");
            return false;
        }
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        UserEntity user = instance.fromUserRegisterDto(registerDto);
        UserEntity byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername != null) {
            log.info("user is not found");
            return false;
        }
        String encode = encoder.encode(registerDto.getPassword());

        user.setEmail(registerDto.getUsername());
        user.setPassword(encode);
        log.info("user is register");
        userRepository.save(user);
        return true;
    }
}