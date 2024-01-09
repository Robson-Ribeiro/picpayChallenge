package com.picpayChallenge.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.picpayChallenge.dtos.UserDto;
import com.picpayChallenge.entities.UserEntity;
import com.picpayChallenge.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getUsers() {
        Sort sort = Sort.by("id").ascending().and(
				Sort.by("name").ascending()	
		);

        List<UserEntity> users = userRepository.findAll(sort);

        return users.stream().map(UserDto::new).toList();
    }

    public UserDto createUser(UserDto user) {
        UserEntity createdUser = userRepository.save(new UserEntity(user));
        return new UserDto(createdUser);
    }

}
