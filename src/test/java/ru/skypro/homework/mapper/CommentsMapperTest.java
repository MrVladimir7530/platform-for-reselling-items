package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentsMapperTest {
    @Autowired
    private CommentsMapper commentsMapper;
    private UserEntity user = new UserEntity();
    private AdEntity ad = new AdEntity();
    private CommentDto commentDto = new CommentDto();
    private CommentEntity commentEntity1 = new CommentEntity();

    @BeforeEach
    public void init() {
        user.setId(1);
        user.setUsername("UserTest");
        user.setFirstName("User");
        user.setLastName("Test");
        user.setPassword("password");
        user.setPhone("phone");
        user.setRole(RoleDto.USER.toString());
        user.setImage("image");
        user.setEmail("email");


        ad.setPk(1);
        ad.setImage("image");
        ad.setPrice(20);
        ad.setTitle("title");
        ad.setDescription("description");
        ad.setUsersByAuthorId(user);


        commentDto.setAuthor(1);
        commentDto.setAuthorFirstName("authorFirstName");
        commentDto.setAuthorImage("authorImage");
        commentDto.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentDto.setPk(1);
        commentDto.setText("text");

        commentEntity1.setPk(2);
        commentEntity1.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentEntity1.setText("text");
        commentEntity1.setUser(user);
        commentEntity1.setAd(ad);


    }
    @Test
    public void shouldCorrectResultFromMethodCommentDtoAndUsersEntityAndAdsEntityToCommentsEntity() {


        CommentEntity commentEntity = commentsMapper.commentDtoAndUsersEntityAndAdsEntityToCommentsEntity(
                 user, ad, commentDto);

        assertNotNull(commentEntity);
        assertEquals(commentDto.getPk(), commentEntity.getPk());
        assertEquals(commentDto.getCreatedAt(), commentEntity.getCreatedAt());
        assertEquals(commentDto.getText(), commentEntity.getText());
        assertEquals(user, commentEntity.getUser());
        assertEquals(ad, commentEntity.getAd());

    }
    @Test
    public void shouldCorrectResultFromMethodCommentsEntityAndUsersEntityToCommentDto() {


        CommentDto commentDto1 = commentsMapper.commentsEntityAndUsersEntityToCommentDto(commentEntity1);

        assertNotNull(commentDto1);
        assertEquals(commentEntity1.getUser().getId(), commentDto1.getAuthor());
        assertEquals(commentEntity1.getUser().getImage(), commentDto1.getAuthorImage());
        assertEquals(commentEntity1.getUser().getFirstName(), commentDto1.getAuthorFirstName());
        assertEquals(commentEntity1.getCreatedAt(), commentDto1.getCreatedAt());
        assertEquals(commentEntity1.getText(), commentDto1.getText());


    }
}
