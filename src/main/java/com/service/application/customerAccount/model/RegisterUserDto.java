package com.service.application.customerAccount.model;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RegisterUserDto {

    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.cl$",
            message = "Ingresar formato correcto de correo")
    private String email;
    private String password;
    private List<Phone> phones;
}
