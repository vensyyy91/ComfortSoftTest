package ru.vensy.comfortsofttest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Контроллер для поиска чисел в файле")
@Validated
public class FindController {
    private final FindService findService;

    @GetMapping("/min")
    @Operation(summary = "Найти N-ное минимальное число в столбце")
    public ResponseEntity<Integer> findMin(
            @RequestParam @Positive @Parameter(description = "Какое по счету минимальное число необходимо найти") int n,
            @RequestParam @NotBlank @Parameter(description = "Путь, по которому находится файл") String path
    ) {
        log.info("Received request GET /min with parameters: n = {}, path={}", n, path);
        return ResponseEntity.ok(findService.findMin(n, path));
    }
}
