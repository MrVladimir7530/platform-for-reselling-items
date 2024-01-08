package ru.skypro.homework.initialization;

import ru.skypro.homework.model.Role;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;

import java.sql.Date;
import java.time.LocalDate;

public final class EntityInitialization {
    public static final UserEntity userEntity = new UserEntity();

    public static final ImageEntity imageEntity = new ImageEntity();
    public static final AdEntity adEntity = new AdEntity();
    public static final CommentEntity commentEntity = new CommentEntity();

    public static ImageEntity getImageEntity() {
        imageEntity.setId(1);
        imageEntity.setPath("Какой-то путь");
        imageEntity.setSize(10L);
        imageEntity.setContentType("Какой-то ContentType");

        return imageEntity;
    }

    public static UserEntity getUserEntity() {
        userEntity.setId(1);
        userEntity.setUsername("UserTest");
        userEntity.setFirstName("User");
        userEntity.setLastName("Test");
        userEntity.setPassword("password");
        userEntity.setPhone("phone");
        userEntity.setRole(Role.USER);
        userEntity.setImageEntity(imageEntity);
        userEntity.setEmail("email");

        return userEntity;
    }

    public static AdEntity getAdEntity() {

        adEntity.setPk(1);
        adEntity.setImageEntity(imageEntity);
        adEntity.setPrice(20);
        adEntity.setTitle("title");
        adEntity.setDescription("description");
        adEntity.setUsersByAuthorId(userEntity);

        return adEntity;
    }

    public static CommentEntity getCommentEntity() {

        commentEntity.setPk(1);
        commentEntity.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentEntity.setText("text");
        commentEntity.setUser(userEntity);
        commentEntity.setAd(adEntity);

        return commentEntity;
    }


}
