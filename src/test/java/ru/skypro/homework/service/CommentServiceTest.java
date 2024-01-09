package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.ImageEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.initialization.DtoInitialization;
import ru.skypro.homework.initialization.EntityInitialization;
import ru.skypro.homework.mapper.CommentsMapper;
import ru.skypro.homework.mapper.CommentsMapperImpl;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.ImagesRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {
    private AdRepository adRepositoryMock;
    private AdService adService;
    private CommentRepository commentRepositoryMock;
    private UserRepository userRepositoryMock;
    private Principal principalMock;
    private CommentService out;
    private CommentsMapper commentsMapper;
    private  ImagesRepository imagesRepository;

    private  AdEntity adEntityInit;
    private  UserEntity user;
    private  ImageEntity imageInit;
    private  CreateOrUpdateCommentDto createOrUpdateCommentDtoInit;
    private CommentEntity commentEntityInit1;
    private CommentEntity commentEntityInit2;

    @BeforeEach
    public void init() {
        commentsMapper = new CommentsMapperImpl();
        adRepositoryMock = mock(AdRepository.class);
        commentRepositoryMock = mock(CommentRepository.class);
        userRepositoryMock = mock(UserRepository.class);
        adService = new AdServiceImpl(userRepositoryMock, adRepositoryMock, imagesRepository);
        out = new CommentServiceImpl(adService, commentsMapper, commentRepositoryMock, userRepositoryMock);
        principalMock = mock(Principal.class);

        commentEntityInit1 = EntityInitialization.getCommentEntity();
        commentEntityInit2 = EntityInitialization.getCommentEntity();
        commentEntityInit2.setPk(2);
        adEntityInit = EntityInitialization.getAdEntity();
        adEntityInit.setComments(List.of(commentEntityInit1, commentEntityInit2));
        imageInit = EntityInitialization.getImageEntity();
        user = EntityInitialization.getUserEntity();
        createOrUpdateCommentDtoInit = DtoInitialization.getCreateOrUpdateCommentDto();
    }

    @Test
    public void shouldCorrectResultFromMethodCreateComment() {
        when(adRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(adEntityInit));
        when(principalMock.getName()).thenReturn("anyString");
        when(userRepositoryMock.findByUsername(anyString())).thenReturn(user);
        when(commentRepositoryMock.save(any(CommentEntity.class))).thenReturn(any(CommentEntity.class));

        CommentDto actual = out.createComment(adEntityInit.getPk(), createOrUpdateCommentDtoInit, principalMock);

        assertNotNull(actual);
        assertEquals(user.getId(), actual.getAuthor());
        assertEquals(user.getImageEntity().getPath(), actual.getAuthorImage());
        assertEquals(user.getFirstName(), actual.getAuthorFirstName());
        assertEquals(Date.valueOf(LocalDate.now()), actual.getCreatedAt());
        assertEquals(createOrUpdateCommentDtoInit.getText(), actual.getText());

        verify(adRepositoryMock, times(1)).findById(anyInt());
        verify(principalMock, times(1)).getName();
        verify(userRepositoryMock, times(1)).findByUsername(anyString());
        verify(commentRepositoryMock, times(1)).save(any(CommentEntity.class));


    }
    @Test
    public void shouldThrowNoSuchElementExceptionFromMethodCreateComment() {
        when(adRepositoryMock.findById(anyInt())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class
                , ()->out.createComment(1, createOrUpdateCommentDtoInit, principalMock));

        verify(adRepositoryMock, times(1)).findById(anyInt());
        verify(commentRepositoryMock, times(0)).save(any(CommentEntity.class));
    }
    @Test
    public void shouldCorrectResultFromMethodGetComments() {
        when(adRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(adEntityInit));

        CommentsDto actual = out.getComments(1);
        List<CommentDto> expectedList = List.of(commentsMapper.commentsEntityToCommentDto(adEntityInit.getComments().get(0))
                , commentsMapper.commentsEntityToCommentDto(adEntityInit.getComments().get(1)));

        assertNotNull(actual);
        assertEquals(adEntityInit.getComments().size(), actual.getCount());
        assertEquals(expectedList, actual.getResults());

        verify(adRepositoryMock, times(1)).findById(anyInt());
    }
    @Test
    public void shouldThrowNoSuchElementExceptionFromMethodGetComments() {
        when(adRepositoryMock.findById(anyInt())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class
                , ()->out.getComments(1));

        verify(adRepositoryMock, times(1)).findById(anyInt());
    }
    @Test
    public void shouldCorrectResultFromMethodEditComment() {
        when(commentRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(commentEntityInit1));
        when(commentRepositoryMock.save(any(CommentEntity.class))).thenReturn(any(CommentEntity.class));

        CommentDto actual = out.editComment(1, 1, createOrUpdateCommentDtoInit);

        assertNotNull(actual);
        assertEquals(commentEntityInit1.getUser().getId(), actual.getAuthor());
        assertEquals(commentEntityInit1.getUser().getImageEntity().getPath(), actual.getAuthorImage());
        assertEquals(commentEntityInit1.getUser().getFirstName(), actual.getAuthorFirstName());
        assertEquals(commentEntityInit1.getCreatedAt(), actual.getCreatedAt());
        assertEquals(commentEntityInit1.getPk(), actual.getPk());
        assertEquals(createOrUpdateCommentDtoInit.getText(), actual.getText());

        verify(commentRepositoryMock, times(1)).findById(anyInt());
        verify(commentRepositoryMock, times(1)).save(any(CommentEntity.class));
    }
    @Test
    public void shouldCorrectResultFromMethodDeleteComment() {
        doNothing().when(commentRepositoryMock).deleteById(anyInt());

        assertEquals(HttpStatus.OK, out.deleteComment(1,1));

        verify(commentRepositoryMock, times(1)).deleteById(anyInt());
    }
}
