package com.picpayChallenge.dtos;

import org.springframework.beans.BeanUtils;

import com.picpayChallenge.entities.TransactionEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class TransactionDto {

    private Long id;

    private Long receiverId;

    private Long payerId;

    private double value;

    public TransactionDto(Long receiverId, Long payerId, double value) {
        this.receiverId = receiverId;
        this.payerId = payerId;
        this.value = value;
    }

    public TransactionDto(TransactionEntity transaction) {
        this.id = transaction.getId();
        this.receiverId = transaction.getReceiver().getId();
        this.payerId = transaction.getPayer().getId();
        this.value = transaction.getValue();
    }
}
