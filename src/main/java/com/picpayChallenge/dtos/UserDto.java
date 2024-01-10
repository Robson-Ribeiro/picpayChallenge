package com.picpayChallenge.dtos;

import org.springframework.beans.BeanUtils;

import com.picpayChallenge.dataTypes.UserType;
import com.picpayChallenge.entities.UserEntity;

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
public class UserDto {

    private Long id;

    @NotBlank
    @NotEmpty
    private String name;

    @NotBlank
    @NotEmpty
    private String surname;

    @NotBlank
    @NotEmpty
    private String document;

    @NotBlank
    @NotEmpty
    private String email;

    @NotBlank
    @NotEmpty
    private String password;

    @NotBlank
    @NotEmpty
    private double balance;

    @NotBlank
    @NotEmpty
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
