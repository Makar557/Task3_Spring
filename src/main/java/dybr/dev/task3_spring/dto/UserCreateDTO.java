package dybr.dev.task3_spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserCreateDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @Min(1)
    @Max(110)
    private Integer age;

}