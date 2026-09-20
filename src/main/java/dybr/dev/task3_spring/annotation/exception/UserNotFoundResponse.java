package dybr.dev.task3_spring.annotation.exception;

import dybr.dev.task3_spring.exception.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class, example = """
            {
                "message": "Пользователь не найден",
                "errorTime": "2026-09-09T22:31:44.6670151"
            }
            """)))
public @interface UserNotFoundResponse {
}
