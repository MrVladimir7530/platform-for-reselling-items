package ru.skypro.homework.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDto {
    private int author;
    private String authorImage;
    private String authorFirstName;
    private LocalDate createdAt;
    private int pk;
    private String text;
}
