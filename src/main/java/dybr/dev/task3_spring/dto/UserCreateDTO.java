package dybr.dev.task3_spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(

        @NotBlank(message = "Имя не должно быть пустым")
        String name,

        @NotBlank(message = "Почта не должна быть пустой")
        @Email(message = "Некорректный email")
        String email,

        @Min(value = 1, message = "Возраст должен быть не меньше 1")
        @Max(value = 110, message = "Возраст должен быть меньше 110")
        Integer age

) {
}