package com.service.application.customerAccount.expose.web;

import com.service.application.customerAccount.business.UserService;
import com.service.application.customerAccount.repository.UserRepository;
import com.service.application.customerAccount.model.*;
import com.service.application.customerAccount.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UserRes> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        UserRes registeredUser = userService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@RequestBody LoginReq loginUserDto) {
        User authenticatedUser = userService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        UserResponse loginResponse = UserResponse.builder().expiresIn(jwtService.getExpirationTime()).token(jwtToken)
                .build();
        return ResponseEntity.ok(loginResponse);
    }
}