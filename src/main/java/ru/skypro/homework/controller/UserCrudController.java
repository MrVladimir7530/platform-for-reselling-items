package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.service.UserCrudService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-crud")
public class UserCrudController {
    private final UserCrudService userCrudService;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestParam String username, @RequestParam String password) {
        try {
            UserEntity user = userCrudService.createUser(username, password);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<UserEntity> getUser(@RequestParam String username) {
        UserEntity user = userCrudService.getUser(username);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user) {
        UserEntity userChange = userCrudService.updateUser(user);
        return ResponseEntity.ok().body(userChange);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam String username) {
        userCrudService.deleteUser(username);
        return ResponseEntity.ok().build();
    }
}
