package dybr.dev.task3_spring.annotation.success;

import dybr.dev.task3_spring.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "201", description = "пользователь создан", content = @Content(mediaType = "application/prs.hal-forms+json", schema = @Schema(implementation = UserResponseDTO.class, example = """
        {
          "_links": {
            "self": {
              "href": "http://localhost:8080/api/users/13"
            },
            "update": {
              "href": "http://localhost:8080/api/users/13"
            },
            "delete": {
              "href": "http://localhost:8080/api/users/13"
            },
            "users": {
              "href": "http://localhost:8080/api/users"
            }
          },
          "id": 13,
          "name": "Makar",
          "email": "makar@gmail.com",
          "age": 31
        }
        """)))
public @interface UserCreatedResponse {
}
