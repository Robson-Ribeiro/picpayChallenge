package com.picpayChallenge.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpayChallenge.dtos.TransactionDto;
import com.picpayChallenge.entities.TransactionEntity;
import com.picpayChallenge.entities.UserEntity;
import com.picpayChallenge.repositories.TransactionRepository;
import com.picpayChallenge.repositories.UserRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public TransactionDto transferFunds(TransactionDto transaction) throws Exception {

        Optional<UserEntity> optPayer = userRepository.findById(transaction.getPayerId());
        
        UserEntity payer = optPayer.get();

        if("common" != payer.getUserType().toString()) {
            throw new Exception("Only common users can transfer their funds!");
        } 
        if(payer.getBalance() < transaction.getValue()) {
            throw new Exception("Your account does not have enough money to make this transaction!");
        }

        Optional<UserEntity> optReceiver = userRepository.findById(transaction.getReceiverId());
        UserEntity receiver = optReceiver.get();

        payer.setBalance(payer.getBalance() - transaction.getValue());
        receiver.setBalance(receiver.getBalance() + transaction.getValue());

        payer = userRepository.save(payer);
        receiver = userRepository.save(receiver);

        TransactionEntity newTransaction = new TransactionEntity(receiver, payer, transaction.getValue());

        TransactionEntity successfulTransaction = transactionRepository.save(newTransaction);

        return new TransactionDto(successfulTransaction);
    }
    
}
