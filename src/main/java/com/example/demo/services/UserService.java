package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @PersistenceContext
    public EntityManager entityManager;

    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public UserDto addNewUser(UserDto userDto) {
        UserEntity user=mapper.map(userDto,UserEntity.class);
       UserEntity output= userRepository.save(user);
        return mapper.map(output,UserDto.class);
    }

    @Transactional
    public UserDto updateUser(Long userId, UserDto userDetails) {
        Optional<UserEntity> existingUserOpt = userRepository.findById(userId);

        UserEntity userEntity;
        if (existingUserOpt.isPresent()) {
            // User exists, update the existing entity
            userEntity = existingUserOpt.get();
            // Update the entity's fields with the values from userDetails
            mapper.map(userDetails, userEntity); // This should update the existing entity's fields
        } else {
            // User does not exist, create a new one
            userEntity = mapper.map(userDetails, UserEntity.class);
            // Don't set the ID, it will be automatically generated
        }
        // Save the entity (both create or update)
        UserEntity savedUser = userRepository.save(userEntity);
        // Return the saved user as a DTO
        return mapper.map(savedUser, UserDto.class);

    }

    @Transactional
    public boolean deleteUser(Long id) {
        boolean exists=userRepository.existsById(id);
        if(!exists) return  false;
        userRepository.deleteById(id);
        entityManager.flush();
        entityManager.clear();
        return  true;
    }

    public List<UserDto> fetchAllUsers() {
        List<UserEntity> users=userRepository.findAll();
        return users.stream().map((item)->{
           return  mapper.map(item,UserDto.class);
        }).collect(Collectors.toList());
    }

    public UserDto updateUserPartially(Long userId, Map<String, Object> update) {
        boolean exists= userRepository.existsById(userId);
        if(!exists)return  null;
        UserEntity userEntity=userRepository.findById(userId).get();
        update.forEach((field,value)->{
            Field fieldToBeUpdated= ReflectionUtils.findRequiredField(UserEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,userEntity,value);
        });
        return mapper.map(userRepository.save(userEntity),UserDto.class);
    }

    public Optional<UserDto> getUserById(Long userId) {
        Optional<UserEntity> user=userRepository.findById(userId);
                return user.map(user1->mapper.map(user1,UserDto.class));
    }
}
