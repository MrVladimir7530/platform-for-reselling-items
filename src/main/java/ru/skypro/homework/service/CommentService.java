package ru.skypro.homework.service;

import org.springframework.http.HttpStatus;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.CommentEntity;

import java.security.Principal;

public interface CommentService {

    CommentEntity findById(Integer id);
    CommentDto createComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto, Principal principal);

    CommentsDto getComments(Integer adId);
    CommentDto editComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto);
    HttpStatus deleteComment(Integer adId, Integer commentId);

}
