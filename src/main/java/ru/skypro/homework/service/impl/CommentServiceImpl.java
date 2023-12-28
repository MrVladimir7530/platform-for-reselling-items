package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;


@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final AdService adService;
    @Override
    public CommentDto createComment(Integer adId) {
        AdEntity adEntity = adService.findById(adId);

        return null;
    }
}
