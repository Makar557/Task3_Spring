package dybr.dev.task3_spring.dto;

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
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    private Integer age;
}