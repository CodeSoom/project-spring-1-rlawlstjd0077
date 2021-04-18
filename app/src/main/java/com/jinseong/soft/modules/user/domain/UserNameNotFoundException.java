package com.jinseong.soft.modules.user.domain;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String username) {
        super("User not found - username: " + username);
    }
}
