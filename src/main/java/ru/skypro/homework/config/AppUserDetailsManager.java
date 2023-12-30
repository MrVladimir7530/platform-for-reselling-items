package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

@RequiredArgsConstructor
public class AppUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;

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
        //todo дописать createUser
    }

    @Override
    public void updateUser(UserDetails user) {
        //todo дописать updateUser
    }

    @Override
    public void deleteUser(String username) {
        //todo дописать deleteUser
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        //todo дописать changePassword
    }

    @Override
    public boolean userExists(String username) {
        //todo дописать userExists
        return false;
    }
}
