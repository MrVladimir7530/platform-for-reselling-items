package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(Integer adId);
}
