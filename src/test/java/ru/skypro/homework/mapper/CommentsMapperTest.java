package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.entity.AdsEntity;
import ru.skypro.homework.entity.CommentsEntity;
import ru.skypro.homework.entity.UsersEntity;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentsMapperTest {
    @Autowired
    private CommentsMapper commentsMapper;
    private UsersEntity user = new UsersEntity();
    private AdsEntity ad = new AdsEntity();
    private CommentDto commentDto = new CommentDto();
    private CommentsEntity commentsEntity1 = new CommentsEntity();

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

        commentsEntity1.setPk(2);
        commentsEntity1.setCreatedAt(Date.valueOf(LocalDate.now()));
        commentsEntity1.setText("text");
        commentsEntity1.setUser(user);
        commentsEntity1.setAd(ad);


    }
    @Test
    public void shouldCorrectResultFromMethodCommentDtoAndUsersEntityAndAdsEntityToCommentsEntity() {


        CommentsEntity commentsEntity = commentsMapper.commentDtoAndUsersEntityAndAdsEntityToCommentsEntity(
                 user, ad, commentDto);

        assertNotNull(commentsEntity);
        assertEquals(commentDto.getPk(), commentsEntity.getPk());
        assertEquals(commentDto.getCreatedAt(), commentsEntity.getCreatedAt());
        assertEquals(commentDto.getText(), commentsEntity.getText());
        assertEquals(user, commentsEntity.getUser());
        assertEquals(ad, commentsEntity.getAd());

    }
    @Test
    public void shouldCorrectResultFromMethodCommentsEntityAndUsersEntityToCommentDto() {


        CommentDto commentDto1 = commentsMapper.commentsEntityAndUsersEntityToCommentDto(user, commentsEntity1);

        assertNotNull(commentDto1);
        assertEquals(commentsEntity1.getUser().getId(), commentDto1.getAuthor());
        assertEquals(commentsEntity1.getUser().getImage(), commentDto1.getAuthorImage());
        assertEquals(commentsEntity1.getUser().getFirstName(), commentDto1.getAuthorFirstName());
        assertEquals(commentsEntity1.getCreatedAt(), commentDto1.getCreatedAt());
        assertEquals(commentsEntity1.getText(), commentDto1.getText());


    }
}
