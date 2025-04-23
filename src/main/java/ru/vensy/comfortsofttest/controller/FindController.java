package ru.vensy.comfortsofttest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vensy.comfortsofttest.service.FindService;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Контроллер для поиска чисел в файле")
public class FindController {
    private final FindService findService;

    @GetMapping("/min")
    @Operation(
            summary = "Найти N-ное минимальное число в столбце",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный ответ",
                            content = @Content(schema = @Schema(example = "-5"))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные входящие данные",
                            content = @Content(schema = @Schema(
                                    example = "Переданное число превышает количество элементов в столбце."
                            ))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Необходимый объект не найден",
                            content = @Content(schema = @Schema(example = "Файл не найден."))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибка чтения файла",
                            content = @Content(schema = @Schema(example = "Ошибка чтения файла: файл поврежден"))
                    )
            }
    )
    public ResponseEntity<Integer> findMin(
            @Parameter(description = "Какое по счету минимальное число необходимо найти",
                    example = "3")
            @RequestParam @Positive int n,
            @Parameter(description = "Путь, по которому находится файл",
                    example = "{your_path}//ComfortSoftTest//excel//test.xlsx")
            @RequestParam @NotBlank String path
    ) {
        log.info("Received request GET /min with parameters: n = {}, path={}", n, path);
        return ResponseEntity.ok(findService.findMin(n, path));
    }
}
