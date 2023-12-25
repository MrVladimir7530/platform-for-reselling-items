package ru.skypro.homework.dto;

import lombok.Data;

import java.sql.Date;


@Data
public class CommentDto {
    private int author;
    private String authorImage;
    private String authorFirstName;
    private Date createdAt;
    private int pk;
    private String text;
}
