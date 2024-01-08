package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Создание комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Созданный комментарий",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommentDto.class)
                                    )
                            }
                    )
            }, tags = "Комментарии"
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Integer id
            , @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto, Principal principal) {
        log.info("Was invoked method for creation of comment in CommentController");

        CommentDto commentDto = commentService.createComment(id, createOrUpdateCommentDto, principal);

        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @Operation(summary = "Получение комментариев обьявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список и количество комментариев",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommentDto.class)
                                    )
                            }
                    )
            }, tags = "Комментарии"
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable Integer id) {
        log.info("Was invoked method for get of comments in CommentController");
        CommentsDto commentsDto = commentService.getComments(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentsDto);
    }

    @Operation(summary = "Изменение комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененный комментарий",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommentDto.class)
                                    )
                            }
                    )
            }, tags = "Комментарии"
    )
    @PreAuthorize("@checkAccess.isAdminOrOwnerComment(#commentId, authentication)")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> editComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId,
            @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        log.info("Was invoked method for update of comment in CommentController");
        CommentDto commentDto = commentService.editComment(adId, commentId, createOrUpdateCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @Operation(summary = "Удаление коментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Статус операции",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CommentDto.class)
                                    )
                            }
                    )
            }, tags = "Комментарии"
    )

    @PreAuthorize("@checkAccess.isAdminOrOwnerComment(#commentId, authentication)")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId) {
        log.info("Was invoked method for delete of comment in CommentController");
        return ResponseEntity.status(commentService.deleteComment(adId, commentId)).build();
    }


}
