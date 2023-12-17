package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {
    /**
     * Создание коментария. Аргументы: Id объявления и текст комментария.
     * @param adId
     * @param comment
     * @return ResponseEntity<CommentDto>
     */
    //todo дописать метод контроллера
    @PostMapping("/ads/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Integer adId, @RequestParam String comment) {
        return null;
    }

    /**
     * Получение комментариев обьявления. На входе Id обьявления
     * @param adId
     * @return ResponseEntity<CommentsDto>
     */
    //todo дописать метод контроллера
    @GetMapping("/ads/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable Integer adId) {
        return null;
    }

    /**
     * Обновление комментария. Аргументы: Id объявления, Id комментария
     * @param adId
     * @param commentId
     * @return ResponseEntity<CommentDto>
     */
    //todo дописать метод контроллера
    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return null;
    }

    /**
     * Удаление коммнетария. Аргументы: Id объявления, Id комментария
     * @param adId
     * @param commentId
     * @return ResponseEntity<HttpStatus>
     */
    //todo дописать метод контроллера
    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> createComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return null;
    }


}