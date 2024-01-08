package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {
    private final AdService adService;
    /**
     * Получение всех объявлений
     * @return ResponseEntity<AdsDto>
     */
    @GetMapping()
    public ResponseEntity<AdsDto> getAds(Principal principal) {
        AdsDto allAds = adService.getAllAds(principal);
        return ResponseEntity.ok(allAds);
    }

    /**
     * Создание объявленияю Аргументы: CreateOrUpdateAdDto(title-заголовок объявления,
     * price - цена объявления, description - описание объявления), image - изображение
     * @param createOrUpdateAdDto
     * @param image
     * @return ResponseEntity<AdDto>
     */
    @PostMapping()
    public ResponseEntity<AdDto> createAd(@RequestBody CreateOrUpdateAdDto createOrUpdateAdDto
            , @RequestParam MultipartFile image) {
        return null;
    }

    /**
     * Получение объявлений авторизованного пользователя
     * @return ResponseEntity<AdsDto>
     */
    @GetMapping("/me")
    public ResponseEntity<AdsDto> getMyAds() {
        return null;
    }

    /**
     * Получение информации об объявлении
     * @param adId
     * @return ResponseEntity<ExtendedAdDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAdInfo(@PathVariable Integer adId) {
        return null;
    }

    /**
     * Обновление информации об объявлении
     * @param adId
     * @return ResponseEntity<CreateOrUpdateAdDto>
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CreateOrUpdateAdDto> editAd(@PathVariable Integer adId) {
        return null;
    }

    /**
     * Обновление картинки объявления
     * @param adId
     * @param image
     * @return ResponseEntity<CreateOrUpdateAdDto
     */
    @PatchMapping("/{id}/image")
    public ResponseEntity<CreateOrUpdateAdDto> editAdImage(@PathVariable Integer adId
            , @RequestParam MultipartFile image) {
        return null;
    }

    /**
     * Удаление объявления по Id
     * @param adId
     * @return ResponseEntity<HttpStatus>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAd(@PathVariable Integer adId) {
        return null;
    }






}
