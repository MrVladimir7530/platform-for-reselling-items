package ru.skypro.homework.controller;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.filter.CheckAccess;
import ru.skypro.homework.initialization.EntityInitialization;
import ru.skypro.homework.mapper.CommentsMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@WebMvcTest(CommentController.class)
public class CommentControllerSecurityTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepositoryMock;
    @MockBean
    private CommentsMapper commentsMapperMock;
    @MockBean
    private CommentRepository commentRepositoryMock;
    @SpyBean
    private CommentServiceImpl commentServiceSpy;
    @MockBean
    private AdRepository adRepositoryMock;
    @SpyBean
    private AdServiceImpl adServiceSpy;
    @SpyBean
    private CheckAccess checkAccessSpy;
    @InjectMocks
    private CommentController commentController;

    private CreateOrUpdateCommentDto createOrUpdateCommentDtoInit;
    private CommentDto commentDtoInit;
    private CommentEntity commentEntityInit;
    private UserEntity userEntityInit;


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        commentEntityInit = EntityInitialization.getCommentEntity();
        userEntityInit = EntityInitialization.getUserEntity();

    }

    //(expected = AuthenticationCredentialsNotFoundException.class)
//    (username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @WithMockUser(username = "UserTest")
    public void shouldCorrectResultFromMethodEditCommentWhenUserIsOwner() throws Exception {
        when(commentRepositoryMock.findById(anyInt())).thenReturn(Optional.of(commentEntityInit));
        when(commentRepositoryMock.save(any(CommentEntity.class))).thenReturn(commentEntityInit);
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(userEntityInit);

        String text = "someText";
        JSONObject createOrUpdateCommentDto = new JSONObject();
        createOrUpdateCommentDto.put("text", text);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1/comments/1")
                        .content(createOrUpdateCommentDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(commentEntityInit.getUser().getId()))
                .andExpect(jsonPath("$.authorImage").value(commentEntityInit.getUser().getImageEntity().getPath()))
                .andExpect(jsonPath("$.authorFirstName").value(commentEntityInit.getUser().getFirstName()))
                .andExpect(jsonPath("$.createdAt").value(commentEntityInit.getCreatedAt()))
                .andExpect(jsonPath("$.createdAt").value(commentEntityInit.getPk()))
                .andExpect(jsonPath("$.text").value(text));


        verify(commentRepositoryMock, times(2)).findById(anyInt());
        verify(commentRepositoryMock, times(1)).save(any(CommentEntity.class));
    }

}
