package com.picpayChallenge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpayChallenge.dtos.TransactionDto;
import com.picpayChallenge.services.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/transaction")
@CrossOrigin
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/")
    public TransactionDto transferFunds(@RequestBody TransactionDto transaction) throws Exception {

        return transactionService.transferFunds(transaction);
    }

}
