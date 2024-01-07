package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.filter.CheckAccess;
import ru.skypro.homework.service.CommentService;

import java.security.Principal;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentController {
    private final CommentService commentService;
    private final CheckAccess checkAccess;
    /**
     * Создание коментария. Аргументы: Id объявления и текст комментария.
     * @param adId
     * @param  createOrUpdateCommentDto
     * @return ResponseEntity<CommentDto>
     */


    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Integer adId
            , @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto, Principal principal) {

        CommentDto commentDto = commentService.createComment(adId, createOrUpdateCommentDto, principal);

        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    /**
     * Получение комментариев обьявления. Аргумент: Id обьявления
     * @param adId
     * @return ResponseEntity<CommentsDto>
     */
    //todo  добавить проверку на админа/собственника
    @GetMapping("/{id}/comments")
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
    @PreAuthorize("@checkAccess.isAdminOrOwnerComment(#commentId, #authentication)")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable Integer adId, @PathVariable Integer commentId
            , @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto, Authentication authentication ) {
        CommentDto commentDto = commentService.editComment(adId, commentId, createOrUpdateCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    /**
     * Удаление коммнетария. Аргументы: Id объявления, Id комментария
     * @param adId
     * @param commentId
     * @return ResponseEntity<HttpStatus>
     */
    //todo добавить проверку на админа/собственника
    @PreAuthorize("@checkAccess.isAdminOrOwnerComment(#commentId, #authentication)")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId
            , Authentication authentication) {
        return ResponseEntity.status(commentService.deleteComment(adId, commentId)).build();
    }


}
