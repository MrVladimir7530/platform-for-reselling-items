package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentsMapperTest {
    @Autowired
    private CommentsMapper commentsMapper;
    private final UserEntity user = new UserEntity();
    private final AdEntity ad = new AdEntity();
    private final CommentDto commentDtoInit = new CommentDto();
    private final CommentEntity commentEntityInit = new CommentEntity();
    private final CommentEntity commentEntityInit1 = new CommentEntity();
    private final ImageEntity imageInit = new ImageEntity();
    private final CreateOrUpdateCommentDto createOrUpdateCommentDtoInit = new CreateOrUpdateCommentDto();


    @BeforeEach
    public void init() {
        user.setId(1);
        user.setUsername("UserTest");
        user.setFirstName("User");
        user.setLastName("Test");
        user.setPassword("password");
        user.setPhone("phone");
        user.setRole(RoleDto.USER.toString());
        user.setImageEntity(imageInit);
        user.setEmail("email");


        ad.setPk(1);
        ad.setImageEntity(imageInit);
        ad.setPrice(20);
        ad.setTitle("title");
        ad.setDescription("description");
        ad.setUsersByAuthorId(user);


        commentDtoInit.setAuthor(1);
        commentDtoInit.setAuthorFirstName("authorFirstName");
        commentDtoInit.setAuthorImage("authorImage");
        commentDtoInit.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentDtoInit.setPk(1);
        commentDtoInit.setText("text");

        commentEntityInit.setPk(2);
        commentEntityInit.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentEntityInit.setText("text");
        commentEntityInit.setUser(user);
        commentEntityInit.setAd(ad);

        commentEntityInit1.setPk(3);
        commentEntityInit1.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentEntityInit1.setText("text1");
        commentEntityInit1.setUser(user);
        commentEntityInit1.setAd(ad);

        imageInit.setId(1);
        imageInit.setPath("Какой-то путь");
        imageInit.setSize(10L);
        imageInit.setContentType("Какой-то ContentType");

        createOrUpdateCommentDtoInit.setText("Какой-то текст");


    }

    @Test
    public void shouldCorrectResultFromMethodCreateOrUpdateCommentDtoAndAdEntityToCommentEntity() {
        CommentEntity commentEntity = commentsMapper.createOrUpdateCommentDtoAndAdEntityToCommentEntity(
                createOrUpdateCommentDtoInit, ad);
        assertNotNull(commentEntity);
        assertEquals(0, commentEntity.getPk());
        assertEquals(Date.valueOf(LocalDate.now()), commentEntity.getCreatedAt());
        assertEquals(createOrUpdateCommentDtoInit.getText(), commentEntity.getText());
        assertEquals(ad.getUsersByAuthorId(), commentEntity.getUser());
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
