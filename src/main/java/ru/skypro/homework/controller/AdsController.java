package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Получить объявление авторизированного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получить объявление авторизированного пользователя",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdsDto.class)
                                    )
                            }
                    )
            }, tags = "Объявления"
    )
    @GetMapping("/me")
    public ResponseEntity<AdsDto> getMyAds(Principal principal) {
        AdsDto allAds = adService.getMyAds(principal);
        return ResponseEntity.ok(allAds);
    }

    @Operation(summary = "Создать объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Созданное объявление",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdDto.class)
                                    )
                            }
                    )
            }, tags = "Объявления"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> createAd(@RequestBody PropertiesDto properties
            , @RequestParam MultipartFile image, Principal principal) {
        try {
            AdDto adDto = adService.addNewAd(properties, image, principal);
            return ResponseEntity.ok(adDto);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
        //todo не работает
    }

    @Operation(summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получить все объявления",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdsDto.class)
                                    )
                            }
                    )
            }, tags = "Объявления"
    )
    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {

        return ResponseEntity.status(HttpStatus.OK).body(adService.getAllAds());
    }

    @Operation(summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получить информацию об объявлении",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ExtendedAdDto.class)
                                    )
                            }
                    )
            }, tags = "Объявления"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAdInfo(@PathVariable Integer id) {
        log.info("Was invoked method for get of info about Ad in CommentController");
        ExtendedAdDto extendedAdDto = adService.getInfoAboutAd(id);
        return ResponseEntity.status(HttpStatus.OK).body(extendedAdDto);
    }

    @Operation(summary = "Обновление информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновление информации об объявлении",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdDto.class)
                                    )
                            }
                    )
            }, tags = "Объявления"
    )
    @PreAuthorize("@checkAccess.isAdminOrOwnerAd(#id, authentication)")
    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> editAd(@PathVariable Integer id, @RequestBody PropertiesDto propertiesDto) {
        AdDto adDto = adService.editAd(id, propertiesDto);
        return ResponseEntity.status(HttpStatus.OK).body(adDto);
    }

    @Operation(summary = "Обновление картинки объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Обновление картинки объявления",
                            content = {
                                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ExtendedAdDto.class)
                                    )
                            }
                    )
            }, tags = "Объявления"
    )
    @PatchMapping("/{id}/image")
    public ResponseEntity<PropertiesDto> editAdImage(@PathVariable Integer adId
            , @RequestParam MultipartFile image) {
        return null;
    }

    @Operation(summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление объявления"

                    )
            }, tags = "Объявления"
    )
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
