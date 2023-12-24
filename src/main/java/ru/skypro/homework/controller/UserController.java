package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mapper.UserMapper;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDto) {
        //todo дописать метод setPassword
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getInfoUser() {
        //todo дописать метод getInfoUser
        return UserMapper.INSTANCE.toDTO();
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateInfoUser(@RequestBody UpdateUserDto updateUserDto) {
        //todo дописать метод updateInfoUser
        return null;
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateAvatarUser(@RequestParam MultipartFile image) {
        //todo дописать метод updateAvatarUser
        return null;
    }
}
