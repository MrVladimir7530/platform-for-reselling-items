package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.PropertiesDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface AdMapper {

    @Mapping(target = "usersByAuthorId", source = "userEntity")
    @Mapping(target = "image", source = "adDto.image")
    @Mapping(target = "description", ignore = true)
    AdEntity adDtoToAdEntity(UserEntity userEntity, AdDto adDto);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "title", source = "propertiesDto.title")
    @Mapping(target = "description", source = "propertiesDto.description")
    @Mapping(target = "price", source = "propertiesDto.price")
    @Mapping(target = "usersByAuthorId", source = "userEntity")
    AdEntity propertiesDtoAndUserEntityAndStringImageToAdEntity(UserEntity userEntity
            , PropertiesDto propertiesDto, String image);

}
