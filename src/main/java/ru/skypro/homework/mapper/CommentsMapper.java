package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.security.Principal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "ad", source = "ad")
    CommentEntity createOrUpdateCommentDtoAndAdEntityToCommentEntity(CreateOrUpdateCommentDto createOrUpdateCommentDto
            , AdEntity ad, UserEntity user);

    @Mapping(target = "pk", source = "commentDto.pk")
    CommentEntity commentDtoAndUsersEntityAndAdsEntityToCommentsEntity(UserEntity user
            , AdEntity ad, CommentDto commentDto);

    @Mapping(target = "authorImage", source = "commentEntity.user.imageEntity.path")
    @Mapping(target = "authorFirstName", source = "commentEntity.user.firstName")
    @Mapping(target = "author", source = "commentEntity.user.id")
    CommentDto commentsEntityToCommentDto(CommentEntity commentEntity);

    List<CommentDto> listCommentEntityToListCommentDto(List<CommentEntity> commentEntityList);

}
