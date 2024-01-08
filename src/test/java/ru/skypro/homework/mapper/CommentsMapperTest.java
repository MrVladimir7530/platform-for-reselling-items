package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.initialization.DtoInitialization;
import ru.skypro.homework.initialization.EntityInitialization;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentsMapperTest {
    @Autowired
    private CommentsMapper commentsMapper;
    private UserEntity user;
    private AdEntity ad;
    private CommentDto commentDtoInit;
    private CommentEntity commentEntityInit;
    private CommentEntity commentEntityInit1;
    private ImageEntity imageInit;
    private CreateOrUpdateCommentDto createOrUpdateCommentDtoInit;


    @BeforeEach
    public void init() {
        user = EntityInitialization.getUserEntity();
        ad = EntityInitialization.getAdEntity();
        commentEntityInit = EntityInitialization.getCommentEntity();
        commentEntityInit1 = EntityInitialization.getCommentEntity();
        commentEntityInit1.setPk(2);
        imageInit = EntityInitialization.getImageEntity();

        commentDtoInit = DtoInitialization.getCommentDto();
        createOrUpdateCommentDtoInit = DtoInitialization.getCreateOrUpdateCommentDto();
    }

    @Test
    public void shouldCorrectResultFromMethodCreateOrUpdateCommentDtoAndAdEntityToCommentEntity() {
        CommentEntity commentEntity = commentsMapper.createOrUpdateCommentDtoAndAdEntityToCommentEntity(
                createOrUpdateCommentDtoInit, ad, user);
        assertNotNull(commentEntity);
        assertEquals(0, commentEntity.getPk());
        assertEquals(Date.valueOf(LocalDate.now()), commentEntity.getCreatedAt());
        assertEquals(createOrUpdateCommentDtoInit.getText(), commentEntity.getText());
        assertEquals(user, commentEntity.getUser());
        assertEquals(ad, commentEntity.getAd());
    }

    @Test
    public void shouldCorrectResultFromMethodCommentDtoAndUsersEntityAndAdsEntityToCommentsEntity() {


        CommentEntity commentEntity = commentsMapper.commentDtoAndUsersEntityAndAdsEntityToCommentsEntity(
                user, ad, commentDtoInit);

        assertNotNull(commentEntity);
        assertEquals(commentDtoInit.getPk(), commentEntity.getPk());
        assertEquals(commentDtoInit.getCreatedAt(), commentEntity.getCreatedAt());
        assertEquals(commentDtoInit.getText(), commentEntity.getText());
        assertEquals(user, commentEntity.getUser());
        assertEquals(ad, commentEntity.getAd());

    }

    @Test
    public void shouldCorrectResultFromMethodCommentsEntityAndUsersEntityToCommentDto() {


        CommentDto commentDto = commentsMapper.commentsEntityToCommentDto(commentEntityInit);

        assertNotNull(commentDto);
        assertEquals(commentEntityInit.getUser().getId(), commentDto.getAuthor());
        assertEquals(commentEntityInit.getUser().getImageEntity().getPath(), commentDto.getAuthorImage());
        assertEquals(commentEntityInit.getUser().getFirstName(), commentDto.getAuthorFirstName());
        assertEquals(commentEntityInit.getCreatedAt(), commentDto.getCreatedAt());
        assertEquals(commentEntityInit.getText(), commentDto.getText());


    }

    @Test
    public void shouldCorrectResultFromMethodListCommentEntityToListCommentDto() {

        List<CommentEntity> commentEntityList = new ArrayList<>(List.of(commentEntityInit, commentEntityInit1));
        CommentDto commentDto = commentsMapper.commentsEntityToCommentDto(commentEntityInit);
        CommentDto commentDto1 = commentsMapper.commentsEntityToCommentDto(commentEntityInit1);

        List<CommentDto> expected = new ArrayList<>(List.of(commentDto, commentDto1));
        List<CommentDto> actual = commentsMapper.listCommentEntityToListCommentDto(commentEntityList);


        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));


    }
}
