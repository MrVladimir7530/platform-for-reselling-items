package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CommentsDto;
import ru.skypro.homework.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentsMapper;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final AdService adService;
    private final CommentsMapper commentsMapper;
    private final CommentRepository commentRepository;
    private  final UserRepository userRepository;


    @Override
    public CommentEntity findById(Integer id) {
        return commentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public CommentDto createComment(Integer adId, CreateOrUpdateCommentDto createOrUpdateCommentDto, Principal principal) {
        log.info("Was invoked method for creation of comment");
        AdEntity adEntity = adService.findById(adId);
        UserEntity userEntity = userRepository.findByUsername(principal.getName());
        CommentEntity commentEntity = commentsMapper.createOrUpdateCommentDtoAndAdEntityToCommentEntity(
                createOrUpdateCommentDto, adEntity, userEntity);
        commentRepository.save(commentEntity);
        return commentsMapper.commentsEntityToCommentDto(commentEntity);
    }

    @Override
    public CommentsDto getComments(Integer adId) {
        log.info("Was invoked method for get of comments in CommentServiceImpl");
        AdEntity adEntity = adService.findById(adId);
        List<CommentEntity> commentEntityList = adEntity.getComments();
        List<CommentDto> commentDtoList = commentsMapper.listCommentEntityToListCommentDto(commentEntityList);

        return new CommentsDto(commentDtoList.size(), commentDtoList);
    }

    @Override
    public CommentDto editComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        log.info("Was invoked method for update of comment in CommentServiceImpl");
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        commentEntity.setText(createOrUpdateCommentDto.getText());
        commentRepository.save(commentEntity);
        return commentsMapper.commentsEntityToCommentDto(commentEntity);
    }

    @Override
    public HttpStatus deleteComment(Integer adId, Integer commentId) {
        log.info("Was invoked method for delete of comment in CommentServiceImpl");
        try {
            commentRepository.deleteById(commentId);
            return HttpStatus.OK;
        } catch (RuntimeException e) {
            return HttpStatus.BAD_REQUEST;
        }

    }
}
