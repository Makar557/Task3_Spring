package dybr.dev.task3_spring.exception;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String message,
        String detailedMessage,
        LocalDateTime errorTime
) {

}
