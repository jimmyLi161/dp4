package com.example.demo.webservice.service.user;

import com.example.demo.data.holidays.Holidays;
import com.example.demo.data.holidays.HolidaysVO;
import com.example.demo.data.user.User;
import com.example.demo.webservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;

public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    @Override
    public User addUserInfo(User user) {
        User userInfo = userRepository.addUserInfo(user);

        return userInfo;
    }

    @Override
    public ResponseEntity deleteUserInfo(long userID) {
        return null;
    }
}
