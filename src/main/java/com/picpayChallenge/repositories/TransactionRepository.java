package com.picpayChallenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpayChallenge.entities.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    
}
