package dybr.dev.task3_spring.service;

import dybr.dev.task3_spring.dto.UserCreateDTO;
import dybr.dev.task3_spring.dto.UserResponseDTO;
import dybr.dev.task3_spring.entity.UserEntity;
import dybr.dev.task3_spring.kafka.UserKafkaProducer;
import dybr.dev.task3_spring.mapper.UserMapper;
import dybr.dev.task3_spring.model.UserOperation;
import dybr.dev.task3_spring.model.UserNotification;
import dybr.dev.task3_spring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserKafkaProducer producer;

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO user) {

        UserEntity userEntity = userMapper.toEntity(user);

        UserEntity saveUserEntity = userRepository.save(userEntity);

        producer.sendMessage(
                new UserNotification(saveUserEntity.getId(), saveUserEntity.getEmail(), UserOperation.USER_CREATION)
        );

        return userMapper.toResponseDto(saveUserEntity);
    }

    public UserResponseDTO findById(Long id) {

        UserEntity findUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        return userMapper.toResponseDto(findUser);
    }

    public List<UserResponseDTO> findAll() {

        List<UserEntity> findUsers = userRepository.findAll();

        return findUsers.stream().map(userMapper::toResponseDto).toList();
    }

    @Transactional
    public UserResponseDTO deleteById(Long id) {

        UserEntity findUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        userRepository.delete(findUser);

        producer.sendMessage(
                new UserNotification(findUser.getId(), findUser.getEmail(), UserOperation.USER_DELETION)
        );

        return userMapper.toResponseDto(findUser);
    }

    @Transactional
    public UserResponseDTO update(UserCreateDTO user, Long id) {

        UserEntity findUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        findUser.setAge(user.getAge());
        findUser.setName(user.getName());
        findUser.setEmail(user.getEmail());

        userRepository.save(findUser);

        return userMapper.toResponseDto(findUser);
    }
}