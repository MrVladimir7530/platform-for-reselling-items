package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    @Override
    public AdEntity findById(Integer id) {
        log.info("Was invoked method for finding AdEntity by ID");
        return adRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
