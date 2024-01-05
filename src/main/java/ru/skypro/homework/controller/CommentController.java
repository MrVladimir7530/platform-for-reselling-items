package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentController {
    private final CommentService commentService;
    /**
     * Создание коментария. Аргументы: Id объявления и текст комментария.
     * @param adId
     * @param  createOrUpdateCommentDto
     * @return ResponseEntity<CommentDto>
     */

    @PostMapping("/ads/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Integer adId
            , @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {

        CommentDto commentDto = commentService.createComment(adId, createOrUpdateCommentDto);

        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    /**
     * Получение комментариев обьявления. Аргумент: Id обьявления
     * @param adId
     * @return ResponseEntity<CommentsDto>
     */
    @GetMapping("/ads/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable Integer adId) {
        CommentsDto commentsDto = commentService.getComments(adId);
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    /**
     * Обновление комментария. Аргументы: Id объявления, Id комментария
     * @param adId
     * @param commentId
     * @param  createOrUpdateCommentDto
     * @return ResponseEntity<CommentDto>
     */
    //todo дописать метод контроллера
    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable Integer adId, @PathVariable Integer commentId
            , @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto ) {
        CommentDto commentDto = commentService.editComment(adId, commentId, createOrUpdateCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    /**
     * Удаление коммнетария. Аргументы: Id объявления, Id комментария
     * @param adId
     * @param commentId
     * @return ResponseEntity<HttpStatus>
     */
    //todo дописать метод контроллера
    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return ResponseEntity.status(commentService.deleteComment(adId, commentId)).build();
    }


}
