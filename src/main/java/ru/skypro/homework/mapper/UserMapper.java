package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.UserEntity;

@Mapper
public interface UserMapper {

    @Mapping(target = "image", source ="imageEntity.path")
    UserDto toUserDto(UserEntity user);

    UpdateUserDto toUpdateUserDto(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity fromUserRegisterDto(RegisterDto registerDto);

}
