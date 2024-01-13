package com.picpayChallenge.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class NotificationDto {
    private String email;
    private double value;

    public NotificationDto(String email, double value) {
        this.email = email;
        this.value = value;
    }

}
