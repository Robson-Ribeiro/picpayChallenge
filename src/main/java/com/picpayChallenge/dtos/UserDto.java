package com.picpayChallenge.dtos;

import org.springframework.beans.BeanUtils;

import com.picpayChallenge.entities.UserEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class UserDto {

    private Long id;

    private String name;

    private String surname;

    private String document;

    private String email;

    private String password;

    private double balance;

    private String userType;

    public UserDto(String name, String surname, String document, String email, String password, double balance, String userType) {
        this.name = name;
        this.surname = surname;
        this.document = document;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.userType = userType;
    }

    public UserDto(UserEntity user) {
        BeanUtils.copyProperties(user, this);
    }    
}
