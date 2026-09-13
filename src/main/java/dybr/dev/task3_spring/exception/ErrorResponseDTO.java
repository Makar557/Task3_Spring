package dybr.dev.task3_spring.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        String message,
        String detailedMessage,
        LocalDateTime errorTime
) {

}
