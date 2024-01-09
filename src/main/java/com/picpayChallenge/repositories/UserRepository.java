package com.picpayChallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpayChallenge.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
