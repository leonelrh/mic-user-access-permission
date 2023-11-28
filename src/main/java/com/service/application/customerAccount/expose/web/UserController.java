package com.service.application.customerAccount.expose.web;

import com.service.application.customerAccount.business.UserService;
import com.service.application.customerAccount.repository.PhoneRepository;
import com.service.application.customerAccount.repository.UserRepository;
import com.service.application.customerAccount.model.User;
import com.service.application.customerAccount.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Validated
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    UserService userService;


    @GetMapping("/user")
    public List<User> getUser() {
       return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public UserRequest getUserAndPhone(@PathVariable("id") String id) {
        return userService.getUserRequest(id);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable("id") String id, @RequestBody User customer) {
        return userService.updateCustomer(customer,id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") String id){
        phoneRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

}