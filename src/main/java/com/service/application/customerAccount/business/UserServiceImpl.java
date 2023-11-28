package com.service.application.customerAccount.business;

import com.service.application.customerAccount.Exception.EmailAlreadyRegisteredException;
import com.service.application.customerAccount.Exception.InvalidExeption;
import com.service.application.customerAccount.Exception.ResourceNotFoundException;
import com.service.application.customerAccount.repository.PhoneRepository;
import com.service.application.customerAccount.repository.UserRepository;
import com.service.application.customerAccount.model.*;
import com.service.application.customerAccount.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${application.password.pattern}")
    String patt;

    @Autowired
    AuthenticationManager authenticationManager;

    public User updateCustomer(User customer, String id){
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setId(id);
            existingUser.setName(customer.getName() != null ? customer.getName() : existingUser.getName());
            existingUser.setEmail(customer.getEmail() != null ? customer.getEmail() : existingUser.getEmail());
            existingUser.setPassword(customer.getPassword() != null ? customer.getPassword() : existingUser.getPassword());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User createUser(UserRequest customer){
        Optional.of(customer.getEmail())
                .filter(email -> userRepository.existsByEmail(email))
                .ifPresent(email -> {
                    throw new EmailAlreadyRegisteredException("El correo ya está registrado");
                });
        User user=User.builder().build();
        return userRepository.save(user);
    }

    public UserRequest getUserRequest(String id) {
        return userRepository.findById(id)
                .map(user -> UserRequest.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .phones(phoneRepository.getPhonesByIdUser(id))
                        .build())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public UserRes signup(RegisterUserDto input) {
        if(userRepository.existsByEmail(input.getEmail())){
            throw new EmailAlreadyRegisteredException("El correo ya está registrado");
        }
        if (!validateField(input.getPassword(), patt)) {
            throw new InvalidExeption("formato incorrecto");
        }
        User user2=registerUser(input);

        UserResponse userResponse=getUse(input);

        return UserRes.builder().id(user2.getId())
                .name(user2.getName()).password(user2.getPassword())
                .email(user2.getEmail()).created(user2.getCreatedAt())
                .modified(user2.getUpdatedAt()).isactive(user2.isEnabled())
                .token(userResponse.getToken()).build();
    }

    public User authenticate(LoginReq input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( input.getEmail(), input.getPassword())
        );
        return userRepository.findByEmail(input.getEmail());
    }

    public boolean validateField(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public User registerUser(RegisterUserDto input){
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        User user = User.builder()
                .id(uuidString)
                .name(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword())).build();
        User user1= userRepository.save(user);
        List<Phone> phones=input.getPhones().stream()
                .peek(phone -> phone.setIdUser(uuidString))
                .collect(Collectors.toList());
        phoneRepository.saveAll(phones);
        return user1;
    }

    public UserResponse getUse(RegisterUserDto registerUserDto){
        LoginReq loginReq=new LoginReq();
        loginReq.setEmail(registerUserDto.getEmail());
        loginReq.setPassword(registerUserDto.getPassword());
        User authenticatedUser = authenticate(loginReq);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return UserResponse.builder().expiresIn(jwtService.getExpirationTime()).token(jwtToken)
                .build();
    }

}