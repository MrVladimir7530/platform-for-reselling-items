package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    @Mapping(target = "pk", source = "commentDto.pk")
    CommentEntity commentDtoAndUsersEntityAndAdsEntityToCommentsEntity(UserEntity user
            , AdEntity ad, CommentDto commentDto);

    @Mapping(target = "authorImage", source = "commentEntity.user.image")
    @Mapping(target = "authorFirstName", source = "commentEntity.user.firstName")
    @Mapping(target = "author", source = "commentEntity.user.id")
    CommentDto commentsEntityAndUsersEntityToCommentDto(CommentEntity commentEntity);

}
