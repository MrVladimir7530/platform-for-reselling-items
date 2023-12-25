package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.service.UserService;

import java.security.Principal;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto, Principal principal) {
        if (userService.setPassword(newPasswordDto, principal)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getInfoUser(Principal principal) {
        UserDto userDto = userService.getInfoUser(principal);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateInfoUser(@RequestBody UpdateUserDto updateUserDto, Principal principal) {
        UpdateUserDto userDto = userService.updateInfoUser(updateUserDto, principal);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateAvatarUser(@RequestParam MultipartFile image, Principal principal) {
        //todo дописать метод updateAvatarUser
        return null;
    }
}
