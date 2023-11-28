package com.service.application.customerAccount.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {


    private String token;

    private long expiresIn;

}
