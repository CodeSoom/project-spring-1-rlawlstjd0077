package com.jinseong.soft.application;

import com.jinseong.soft.domain.User;
import com.jinseong.soft.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User deleteUser(Long id) {
        return null;
    }
}
