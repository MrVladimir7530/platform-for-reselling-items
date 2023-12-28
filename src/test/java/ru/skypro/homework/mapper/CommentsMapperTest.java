package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentsMapperTest {
    @Autowired
    private CommentsMapper commentsMapper;
    private final UserEntity user = new UserEntity();
    private final AdEntity ad = new AdEntity();
    private final CommentDto commentDtoInit = new CommentDto();
    private final CommentEntity commentEntityInit = new CommentEntity();
    private final ImageEntity imageInit = new ImageEntity();


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

        imageInit.setId(1);
        imageInit.setPath("Какой-то путь");
        imageInit.setSize(10L);
        imageInit.setContentType("Какой-то ContentType");


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


        CommentDto commentDto = commentsMapper.commentsEntityAndUsersEntityToCommentDto(commentEntityInit);

        assertNotNull(commentDto);
        assertEquals(commentEntityInit.getUser().getId(), commentDto.getAuthor());
        assertEquals(commentEntityInit.getUser().getImageEntity().getPath(), commentDto.getAuthorImage());
        assertEquals(commentEntityInit.getUser().getFirstName(), commentDto.getAuthorFirstName());
        assertEquals(commentEntityInit.getCreatedAt(), commentDto.getCreatedAt());
        assertEquals(commentEntityInit.getText(), commentDto.getText());


    }
}
