package com.picpayChallenge.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
@Entity
@Table(name = "Transactions")
public class TransactionEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "receiver_id")
    private UserEntity receiver;

    @ManyToOne
    @JoinColumn(nullable = false, name = "payer_id")
    private UserEntity payer;

    @Column(nullable = false)
    private double value;

    public TransactionEntity(UserEntity receiver, UserEntity payer, double value) {
        this.receiver = receiver;
        this.payer = payer;
        this.value = value;
    }

}
