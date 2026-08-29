package dybr.dev.task3_spring.exception;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleEntityException(Exception e) {

        var errorDto = new ErrorResponseDto("Произошла ошибка", e.getMessage(), LocalDateTime.now());

        logger.error("{} {} в {}", errorDto.errorTime(), errorDto.message(), errorDto.detailedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(EntityNotFoundException e) {

        var errorDto = new ErrorResponseDto("Пользователь не найден", e.getMessage(), LocalDateTime.now());

        logger.warn("{} {} в {}", errorDto.errorTime(), errorDto.message(), errorDto.detailedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(exception = {IllegalArgumentException.class, MethodArgumentNotValidException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponseDto> handleBadRequest(Exception e) {

        var errorDto = new ErrorResponseDto("Неправильный запрос", e.getMessage(), LocalDateTime.now());

        logger.warn("{} {} в {}", errorDto.errorTime(), errorDto.message(), errorDto.detailedMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
}
