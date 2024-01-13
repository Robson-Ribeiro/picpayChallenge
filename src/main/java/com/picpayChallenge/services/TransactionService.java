package com.picpayChallenge.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpayChallenge.dtos.NotificationDto;
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

    @Autowired
    private RestTemplate restTemplate;

    public TransactionDto transferFunds(TransactionDto transaction) throws Exception {

        Optional<UserEntity> optPayer = userRepository.findById(transaction.getPayerId());
        UserEntity payer = optPayer.get();

        if("common" != payer.getUserType().toString()) {
            throw new Exception("Only common users can transfer their funds!");
        } 
        if(payer.getBalance() < transaction.getValue()) {
            throw new Exception("Your account does not have enough money to make this transaction!");
        }


        ResponseEntity<Map> ApiAuthorization = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if(ApiAuthorization.getStatusCode() != HttpStatus.OK) {
            throw new Exception("The system could not obtain an authorization for your transaction.");
        } 
        if(!"Autorizado".equalsIgnoreCase(ApiAuthorization.getBody().get("message").toString())) {
            throw new Exception("It's not possible to do this transaction.");
        }


        Optional<UserEntity> optReceiver = userRepository.findById(transaction.getReceiverId());
        UserEntity receiver = optReceiver.get();

        payer.setBalance(payer.getBalance() - transaction.getValue());
        receiver.setBalance(receiver.getBalance() + transaction.getValue());


        TransactionEntity newTransaction = new TransactionEntity(receiver, payer, transaction.getValue());

        TransactionEntity successfulTransaction = transactionRepository.save(newTransaction);

        userRepository.save(payer);
        userRepository.save(receiver);

        NotificationDto notificationDto = new NotificationDto(receiver.getEmail(), transaction.getValue());

        ResponseEntity<Map> notificationResponse = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationDto, Map.class);

        if(notificationResponse.getStatusCode() != HttpStatus.OK || !"true".equalsIgnoreCase(notificationResponse.getBody().get("message").toString())) {
            throw new Exception("Could not notificate the receiver's e-mail.");
        }

        return new TransactionDto(successfulTransaction);
    }
    
}
