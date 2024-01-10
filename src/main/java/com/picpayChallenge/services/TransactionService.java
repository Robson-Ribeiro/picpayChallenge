package com.picpayChallenge.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.picpayChallenge.dtos.TransactionDto;
import com.picpayChallenge.entities.TransactionEntity;
import com.picpayChallenge.entities.UserEntity;
import com.picpayChallenge.repositories.TransactionRepository;
import com.picpayChallenge.repositories.UserRepository;

public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public TransactionDto transferFunds(TransactionDto transaction) throws Exception {

        Optional<UserEntity> payer = userRepository.findById(transaction.getPayerId());

        if("common" != payer.get().getUserType().toString()) {
            throw new Exception("Only common users can transfer their funds!");
        } 
        if(payer.get().getBalance() < transaction.getValue()) {
            throw new Exception("Your account does not have enough money to make this transaction!");
        }

        Optional<UserEntity> receiver = userRepository.findById(transaction.getReceiverId());

        payer.get().setBalance(payer.get().getBalance() - transaction.getValue());
        receiver.get().setBalance(receiver.get().getBalance() + transaction.getValue());

        userRepository.save(payer.get());
        userRepository.save(receiver.get());

        TransactionEntity newTransaction = new TransactionEntity(receiver.get(), payer.get(), transaction.getValue());

        TransactionEntity successfulTransaction = transactionRepository.save(newTransaction);

        return new TransactionDto(successfulTransaction);
    }
    
}
