package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class AppUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(RoleDto.USER.name())
                .build();
    }

    @Override
    public void createUser(UserDetails user) {
        String password = user.getPassword();
        String username = user.getUsername();

        UserEntity newUser = new UserEntity();

        String encodePassword = passwordEncoder.encode(password);
        newUser.setPassword(encodePassword);
        newUser.setUsername(username);

        userRepository.save(newUser);

    }

    @Override
    public void updateUser(UserDetails user) {
        String username = user.getUsername();
        String password = user.getPassword();

        UserEntity byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            byUsername = new UserEntity();
        }

        byUsername.setUsername(username);
        byUsername.setPassword(password);

        userRepository.save(byUsername);
    }

    @Override
    public void deleteUser(String username) {
        UserEntity byUsername = userRepository.findByUsername(username);
        if (byUsername != null) {
            userRepository.delete(byUsername);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
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
    public boolean userExists(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }
}
