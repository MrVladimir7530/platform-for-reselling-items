package ru.skypro.homework.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class CommentsDto {
    private final Integer count;
    private final List<CommentDto> results;
}
