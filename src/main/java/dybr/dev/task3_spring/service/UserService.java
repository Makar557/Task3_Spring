package dybr.dev.task3_spring.service;

import dybr.dev.task3_spring.dto.UserCreateDTO;
import dybr.dev.task3_spring.dto.UserResponseDTO;
import dybr.dev.task3_spring.entity.UserEntity;
import dybr.dev.task3_spring.kafka.UserKafkaProducer;
import dybr.dev.task3_spring.mapper.UserMapper;
import dybr.dev.task3_spring.model.OperationsOnUser;
import dybr.dev.task3_spring.model.UserNotification;
import dybr.dev.task3_spring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserKafkaProducer producer;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserKafkaProducer producer) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.producer = producer;
    }

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO user) {

        UserEntity userEntity = userMapper.toEntity(user);

        UserEntity saveUserEntity = userRepository.save(userEntity);

        producer.sendMessage(
                new UserNotification(saveUserEntity.getId(), saveUserEntity.getEmail(), OperationsOnUser.USER_CREATION)
        );

        return userMapper.toDtoResponse(saveUserEntity);
    }

    public UserResponseDTO findById(Long id) {

        UserEntity findUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        return userMapper.toDtoResponse(findUser);
    }

    public List<UserResponseDTO> findAll() {

        List<UserEntity> findUsers = userRepository.findAll();

        return findUsers.stream().map(userMapper::toDtoResponse).toList();
    }

    @Transactional
    public UserResponseDTO deleteById(Long id) {

        UserEntity findUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        userRepository.delete(findUser);

        producer.sendMessage(
                new UserNotification(findUser.getId(), findUser.getEmail(), OperationsOnUser.USER_DELETION)
        );

        return userMapper.toDtoResponse(findUser);
    }

    @Transactional
    public UserResponseDTO update(UserCreateDTO user, Long id) {

        UserEntity findUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        findUser.setAge(user.getAge());
        findUser.setName(user.getName());
        findUser.setEmail(user.getEmail());

        userRepository.save(findUser);

        return userMapper.toDtoResponse(findUser);
    }
}