package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.PropertiesDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface AdMapper {


    @Mapping(target = "usersByAuthorId", source = "userEntity")
    @Mapping(target = "description", ignore = true)
    AdEntity adDtoAndUserEntityToAdEntity(UserEntity userEntity, AdDto adDto, ImageEntity image);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "title", source = "propertiesDto.title")
    @Mapping(target = "description", source = "propertiesDto.description")
    @Mapping(target = "price", source = "propertiesDto.price")
    @Mapping(target = "usersByAuthorId", source = "userEntity")
    @Mapping(target = "imageEntity", source = "imageEntity")
    AdEntity propertiesDtoAndUserEntityAndStringImageToAdEntity(UserEntity userEntity
            , PropertiesDto propertiesDto, ImageEntity imageEntity);


    @Mapping(target = "authorLastName", source = "userEntity.lastName")
    @Mapping(target = "authorFirstName", source = "userEntity.firstName")
    @Mapping(target = "image", source = "adEntity.imageEntity.path")
    ExtendedAdDto adEntityAndUserEntityToExtendedAdDto(UserEntity userEntity, AdEntity adEntity);

    @Mapping(target = "author", source = "adEntity.usersByAuthorId.id")
    @Mapping(target = "image", source = "adEntity.imageEntity.path")
    AdDto adEntityToAdDto(AdEntity adEntity);
}
