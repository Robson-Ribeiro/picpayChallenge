package com.picpayChallenge.dtos;

import com.picpayChallenge.entities.TransactionEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank( message = "The receiverId field is mandatory!")
    @NotEmpty( message = "The receiverId field is mandatory!")
    private Long receiverId;

    @NotBlank( message = "The payerId field is mandatory!")
    @NotEmpty( message = "The payerId field is mandatory!")
    private Long payerId;

    @NotBlank( message = "The value field is mandatory!")
    @NotEmpty( message = "The value field is mandatory!")
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
