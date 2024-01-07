package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.model.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    private AuthService authService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private String username = "Volkov@mail.ru";
    private String password = "vladimir";
    private String email = username;
    private String phone = "+77777777777";
    private String lastName = "volkov";
    private Role role = Role.USER;

    @BeforeEach
    public void init() {
        passwordEncoder = new BCryptPasswordEncoder();
        userRepository = Mockito.mock(UserRepository.class);
        authService = new AuthServiceImpl(userRepository, passwordEncoder);

    }

    @Test
    public void loginTest() {
        UserEntity user = createUser();
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        boolean result = authService.login(username, password);
        assertTrue(result);
    }

    @Test
    public void registerTestByTrue() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        RegisterDto registerDto = createRegisterDto();
        boolean result = authService.register(registerDto);
        assertTrue(result);
    }

    @Test
    public void registerTestByFalse() {
        UserEntity user = createUser();
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        RegisterDto registerDto = createRegisterDto();
        boolean result = authService.register(registerDto);
        assertFalse(result);
    }

    private UserEntity createUser() {
        UserEntity user = new UserEntity();
        String encode = passwordEncoder.encode(password);
        user.setUsername(username);
        user.setFirstName(username);
        user.setPassword(encode);
        user.setEmail(email);
        user.setPhone(phone);
        user.setLastName(lastName);
        user.setRole(role);
        return user;
    }

    private RegisterDto createRegisterDto() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(username);
        registerDto.setPassword(password);
        registerDto.setFirstName(username);
        registerDto.setLastName(lastName);
        registerDto.setPhone(phone);
        registerDto.setRole(role);
        return registerDto;
    }
}
