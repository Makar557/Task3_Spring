package dybr.dev.task3_spring.mapper;

import dybr.dev.task3_spring.dto.UserCreateDTO;
import dybr.dev.task3_spring.dto.UserResponseDTO;
import dybr.dev.task3_spring.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserCreateDTO dto) {

        return new UserEntity(dto.getName(), dto.getEmail(), dto.getAge());

    }

    public UserResponseDTO toResponseDto(UserEntity entity) {

        return new UserResponseDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getAge());

    }
}
