package dybr.dev.task3_spring.annotation.success;

import dybr.dev.task3_spring.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "200", description = "Список пользователей", content = @Content(mediaType = "application/prs.hal-forms+json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class, example = """
           {
              "_embedded": {
            "userResponseDTOList": [
              {
                "_links": {
                  "self": {
                    "href": "http://localhost:8080/api/users/41"
                  },
                  "update": {
                    "href": "http://localhost:8080/api/users/41"
                  },
                  "delete": {
                    "href": "http://localhost:8080/api/users/41"
                  }
                },
                "id": 41,
                "name": "Makar",
                "email": "makar@gmail.com",
                "age": 31
              }
            ]
          },
          "_links": {
            "users": {
              "href": "http://localhost:8080/api/users"
            }
          }
        }
           }"""))))
public @interface UsersListResponse {

}