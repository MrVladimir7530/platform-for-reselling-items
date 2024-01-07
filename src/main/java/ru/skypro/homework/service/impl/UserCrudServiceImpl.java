package ru.skypro.homework.service.impl;

import liquibase.pro.packaged.E;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserCrudService;
@Service
@RequiredArgsConstructor
public class UserCrudServiceImpl implements UserCrudService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(String username, String password) throws Exception {
        UserEntity user = new UserEntity();
        UserEntity byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            String encodeNewPassword = passwordEncoder.encode(password);
            user.setUsername(username);
            user.setPassword(encodeNewPassword);
            return userRepository.save(user);
        }
        throw new Exception();
    }

    @Override
    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        UserEntity byUsername = userRepository.findByUsername(username);
        userRepository.delete(byUsername);
    }
}
