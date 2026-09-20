package dybr.dev.task3_spring.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception e) {

        var errorDto = new ErrorResponseDTO("Внутренняя ошибка сервера", null, LocalDateTime.now());

        logger.error("Непредвиденная ошибка: {}", e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFound(EntityNotFoundException e) {

        var errorDto = new ErrorResponseDTO("Пользователь не найден", null, LocalDateTime.now());

        logger.warn("Пользователь не найден: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(MethodArgumentNotValidException e) {

        String errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        var errorDto = new ErrorResponseDTO("Некорректный запрос", errors, LocalDateTime.now());

        logger.warn("Ошибка валидации запроса: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException e) {

        var errorDto = new ErrorResponseDTO("Некорректный аргумент", "Параметр ID должен иметь тип Long", LocalDateTime.now());

        logger.warn("Некорректный тип аргумента: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleMessageNotReadable(HttpMessageNotReadableException e) {

        var errorDto = new ErrorResponseDTO("Некорректный JSON","Некорректный формат JSON", LocalDateTime.now());

        logger.warn("Некорректный формат JSON: {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

}
