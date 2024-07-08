package com.example.demo.webservice.service.user;

import com.example.demo.data.user.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User addUserInfo(User user);
    ResponseEntity deleteUserInfo(long userID);
}
