package ru.skypro.homework.initialization;

import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.PropertiesDto;

import java.sql.Date;
import java.time.LocalDate;

public final class DtoInitialization {

    public static final CommentDto commentDto = new CommentDto();
    public static final CreateOrUpdateCommentDto createOrUpdateCommentDto = new CreateOrUpdateCommentDto();

    private static final AdDto adDto = new AdDto();
    private static final PropertiesDto propertiesDto = new PropertiesDto();


    public static CommentDto getCommentDto() {
        commentDto.setAuthor(1);
        commentDto.setAuthorFirstName("authorFirstName");
        commentDto.setAuthorImage("authorImage");
        commentDto.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentDto.setPk(1);
        commentDto.setText("text");
        return commentDto;
    }

    public static CreateOrUpdateCommentDto getCreateOrUpdateCommentDto() {
        createOrUpdateCommentDto.setText("Какой-то текст");
        return createOrUpdateCommentDto;
    }

    public static AdDto getAdDto() {
        adDto.setAuthor(1);
        adDto.setImage(EntityInitialization.getImageEntity().getPath());
        adDto.setPk(1);
        adDto.setPrice(20);
        adDto.setTitle("title");
        return adDto;
    }
    public static PropertiesDto getPropertiesDto() {
        propertiesDto.setTitle("title");
        propertiesDto.setPrice(30);
        propertiesDto.setDescription("Description");
        return propertiesDto;
    }




}
