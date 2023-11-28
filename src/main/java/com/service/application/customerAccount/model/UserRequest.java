package com.service.application.customerAccount.model;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.cl$")
    private String email;
    private String password;
    private List<Phone> phones;

}