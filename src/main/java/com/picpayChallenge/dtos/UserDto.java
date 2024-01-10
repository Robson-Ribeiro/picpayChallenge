package com.picpayChallenge.dtos;

import org.springframework.beans.BeanUtils;

import com.picpayChallenge.dataTypes.UserType;
import com.picpayChallenge.entities.UserEntity;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String document;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private double balance;

    @NotBlank
    private UserType userType;

    public UserDto(String name, String surname, String document, String email, String password, double balance, UserType userType) {
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
