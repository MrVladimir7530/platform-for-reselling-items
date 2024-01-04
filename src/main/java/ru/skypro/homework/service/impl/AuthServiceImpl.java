package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

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
            return false;
        }
        return encoder.matches(password, user.getPassword());
//        if (!manager.userExists(userName)) {
//            return false;
//        }
//        UserDetails userDetails = manager.loadUserByUsername(userName);
//        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        UserEntity user = instance.fromUserRegisterDto(registerDto);
        UserEntity byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername != null) {
            return false;
        }
        String encode = encoder.encode(registerDto.getPassword());

        user.setRole(registerDto.getRoleDto());
        user.setEmail(registerDto.getUsername());
        user.setPassword(encode);
        userRepository.save(user);

//        if (manager.userExists(registerDto.getUsername())) {
//            return false;
//        }
//        UserDetails build = User.builder()
//                .passwordEncoder(this.encoder::encode)
//                .password(registerDto.getPassword())
//                .username(registerDto.getUsername())
//                .roles(registerDto.getRoleDto().name())
//                .build();
//        manager.createUser(build);
        return true;
    }

}
