package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.NoSuchElementException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
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
     * @param properties
     * @param image
     * @return ResponseEntity<AdDto>
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> createAd(@RequestPart("properties") @Valid PropertiesDto properties
            , @RequestPart("image") MultipartFile image, Principal principal) {
        try {
            AdDto adDto = adService.addNewAd(properties, image, principal);
            return ResponseEntity.ok(adDto);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
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
    public ResponseEntity<PropertiesDto> editAd(@PathVariable Integer adId) {
        return null;
    }

    /**
     * Обновление картинки объявления
     * @param adId
     * @param image
     * @return ResponseEntity<CreateOrUpdateAdDto
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> editAdImage(@PathVariable Integer adId
            , @RequestParam MultipartFile image) {
        return null;
    }

    /**
     * Удаление объявления по Id
     * @param id
     * @return ResponseEntity<HttpStatus>
     */
    @PreAuthorize("@checkAccess.isAdminOrOwnerAd(#id, authentication)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Integer id) {
        try {
            adService.deleteAd(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }






}
