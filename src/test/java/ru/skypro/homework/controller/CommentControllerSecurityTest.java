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
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.filter.CheckAccess;
import ru.skypro.homework.initialization.EntityInitialization;
import ru.skypro.homework.mapper.CommentsMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    private ImagesRepository imagesRepository;
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
    private CommentEntity commentEntityInit2;
    private UserEntity userEntityInit;
    private AdEntity adEntityInit;
    private final List<CommentEntity> commentEntityList = new ArrayList<>();


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userEntityInit = EntityInitialization.getUserEntity();
        commentEntityInit = EntityInitialization.getCommentEntity();
        commentEntityInit2 = EntityInitialization.getCommentEntity();
        commentEntityInit2.setPk(2);
        adEntityInit = EntityInitialization.getAdEntity();
        adEntityInit.setComments(commentEntityList);


    }

    //(expected = AuthenticationCredentialsNotFoundException.class)
//    (username = "admin", roles = {"USER", "ADMIN"})
    @Test
    @WithMockUser(username = "UserTest")
    //todo поправить тест
    public void shouldCorrectResultFromMethodCreateComment() throws Exception {
        when(adRepositoryMock.findById(anyInt())).thenReturn(Optional.of(adEntityInit));
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(userEntityInit);
        when(commentRepositoryMock.save(any(CommentEntity.class))).thenReturn(commentEntityInit);

        String text = "someText";
        JSONObject createOrUpdateCommentDto = new JSONObject();
        createOrUpdateCommentDto.put("text", text);

        mvc.perform(MockMvcRequestBuilders
                        .post("/ads/1/comments")
                        .with(csrf())
                        .content(createOrUpdateCommentDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(userEntityInit.getId()))
                .andExpect(jsonPath("$.authorImage").value(userEntityInit.getImageEntity().getPath()))
                .andExpect(jsonPath("$.authorFirstName").value(userEntityInit.getFirstName()))
                .andExpect(jsonPath("$.createdAt").value(Date.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.text").value(text));


        verify(adRepositoryMock, times(1)).findById(anyInt());
        verify(userRepositoryMock, times(1)).findByUsername(anyString());
        verify(commentRepositoryMock, times(1)).save(any(CommentEntity.class));

    }
    @Test
    public void shouldTrowStatus403FromMethodCreateComment() throws Exception {
        when(adRepositoryMock.findById(anyInt())).thenReturn(Optional.of(adEntityInit));
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(userEntityInit);
        when(commentRepositoryMock.save(any(CommentEntity.class))).thenReturn(commentEntityInit);

        String text = "someText";
        JSONObject createOrUpdateCommentDto = new JSONObject();
        createOrUpdateCommentDto.put("text", text);

        mvc.perform(MockMvcRequestBuilders
                        .post("/ads/1/comments")
                        .with(csrf())
                        .content(createOrUpdateCommentDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());


        verify(adRepositoryMock, times(0)).findById(anyInt());
        verify(userRepositoryMock, times(0)).findByUsername(anyString());
        verify(commentRepositoryMock, times(0)).save(any(CommentEntity.class));

    }
    @Test
    @WithMockUser(username = "UserTest")
    //todo поправить тест
    public void shouldCorrectResultFromMethodEditCommentWhenUserIsOwner() throws Exception {
        when(commentRepositoryMock.findById(anyInt())).thenReturn(Optional.of(commentEntityInit));
        when(commentRepositoryMock.save(any(CommentEntity.class))).thenReturn(commentEntityInit);
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(userEntityInit);

        String text = "someText";
        JSONObject createOrUpdateCommentDto = new JSONObject();
        createOrUpdateCommentDto.put("text", text);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1/comments/1")
                        .with(csrf())
                        .content(createOrUpdateCommentDto.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(commentEntityInit.getUser().getId()))
                .andExpect(jsonPath("$.authorImage").value(commentEntityInit.getUser().getImageEntity().getPath()))
                .andExpect(jsonPath("$.authorFirstName").value(commentEntityInit.getUser().getFirstName()))
                .andExpect(jsonPath("$.createdAt").value(commentEntityInit.getCreatedAt()))
                .andExpect(jsonPath("$.pk").value(commentEntityInit.getPk()))
                .andExpect(jsonPath("$.text").value(text));


        verify(commentRepositoryMock, times(2)).findById(anyInt());
        verify(commentRepositoryMock, times(1)).save(any(CommentEntity.class));
        verify(userRepositoryMock, times(1)).findByUsername(anyString());
    }
    @Test
    @WithMockUser(username = "UserTest")
    public void shouldCorrectResultFromMethodGetComments() throws Exception {
        when(adRepositoryMock.findById(anyInt())).thenReturn(Optional.of(adEntityInit));

        mvc.perform(MockMvcRequestBuilders
                        .get("/ads/1/comments")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(adEntityInit.getComments().size()));

        verify(adRepositoryMock, times(1)).findById(anyInt());
    }
    @Test
    @WithMockUser(username = "UserTest")
    //todo поправить тест
    public void shouldCorrectResultFromMethodDeleteComment() throws Exception {
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(userEntityInit);
        when(commentRepositoryMock.findById(anyInt())).thenReturn(Optional.of(commentEntityInit));

        doNothing().when(commentRepositoryMock).deleteById(anyInt());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1/comments/1")
                .with(csrf()))
                .andExpect(status().isOk());

        verify(commentRepositoryMock, times(1)).deleteById(anyInt());
    }

}
