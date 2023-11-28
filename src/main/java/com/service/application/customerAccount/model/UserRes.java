package com.service.application.customerAccount.model;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRes {

    private String id;
    private String name;
    private String email;
    private String password;
    private Date created;
    private Date modified;
    private String token;
    private String lastLogin;
    private boolean isactive;

}