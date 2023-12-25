package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.CommentsEntity;
import ru.skypro.homework.entity.UsersEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    CommentsEntity commentDtoAndUsersEntityAndAdsEntityToCommentsEntity(UsersEntity user, AdsEntity ad, CommentDto commentDto);

    @Mapping(target = "authorImage", source = "user.image")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "author", source = "user.id")
    CommentDto commentsEntityToCommentDto(UsersEntity user, CommentsEntity commentsEntity);



}
