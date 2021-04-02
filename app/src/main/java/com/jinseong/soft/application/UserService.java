package com.jinseong.soft.application;

import com.jinseong.soft.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User createUser(User givenUser) {
        return givenUser;
    }
}
