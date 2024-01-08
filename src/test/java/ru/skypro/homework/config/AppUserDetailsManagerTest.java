package ru.skypro.homework.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AppUserDetailsManagerTest {
    private UserRepository userRepository;
    private AppUserDetailsManager appUserDetailsManager;
    private String username = "volkov@mail.ru";
    private String password = "vladimir";

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        appUserDetailsManager = new AppUserDetailsManager(userRepository);
    }

    @Test
    public void loadUserByUsernameTest() {
        UserEntity user = createUser();
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = appUserDetailsManager.loadUserByUsername(username);

        UserDetails expexted = createUserDetails();
        assertEquals(expexted, userDetails);
    }

    private UserDetails createUserDetails() {
        return User.builder()
                .username(username)
                .password(password)
                .roles(Role.USER.name())
                .build();
    }

    private UserEntity createUser() {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
