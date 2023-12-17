package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdsController {
    /**
     * Создание объявленияю Аргументы: CreateOrUpdateAdDto(title-заголовок объявления,
     * price - цена объявления, description - описание объявления), image - изображение
     * @param createOrUpdateAdDto
     * @param image
     * @return ResponseEntity<AdDto>
     */
    @PostMapping("/ads")
    public ResponseEntity<AdDto> createAd(@RequestBody CreateOrUpdateAdDto createOrUpdateAdDto
            , @RequestParam MultipartFile image) {
        return null;
    }

    /**
     * Получение всех объявлений
     * @return ResponseEntity<AdsDto>
     */
    @GetMapping("/ads")
    public ResponseEntity<AdsDto> getAds() {
        return null;
    }

    /**
     * Получение объявлений авторизованного пользователя
     * @return ResponseEntity<AdsDto>
     */
    @GetMapping("/ads/me")
    public ResponseEntity<AdsDto> getMyAds() {
        return null;
    }

    /**
     * Получение информации об объявлении
     * @param adId
     * @return ResponseEntity<ExtendedAdDto>
     */
    @GetMapping("/ads/{id}")
    public ResponseEntity<ExtendedAdDto> getAdInfo(@PathVariable Integer adId) {
        return null;
    }

    /**
     * Обновление информации об объявлении
     * @param adId
     * @return ResponseEntity<CreateOrUpdateAdDto>
     */
    @PatchMapping("/ads/{id}")
    public ResponseEntity<CreateOrUpdateAdDto> editAd(@PathVariable Integer adId) {
        return null;
    }

    /**
     * Обновление картинки объявления
     * @param adId
     * @param image
     * @return ResponseEntity<CreateOrUpdateAdDto
     */
    @PatchMapping("/ads/{id}/image")
    public ResponseEntity<CreateOrUpdateAdDto> editAdImage(@PathVariable Integer adId
            , @RequestParam MultipartFile image) {
        return null;
    }

    /**
     * Удаление объявления по Id
     * @param adId
     * @return ResponseEntity<HttpStatus>
     */
    @DeleteMapping("/ads/{id}")
    public ResponseEntity<HttpStatus> deleteAd(@PathVariable Integer adId) {
        return null;
    }






}
