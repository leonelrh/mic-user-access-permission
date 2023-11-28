package com.service.application.customerAccount.business;

import com.service.application.customerAccount.model.*;

public interface UserService {

    User updateCustomer(User customer, String id);
    User createUser(UserRequest customer);
    UserRequest getUserRequest(String id);
    UserRes signup(RegisterUserDto input);
    User authenticate(LoginReq input);
}