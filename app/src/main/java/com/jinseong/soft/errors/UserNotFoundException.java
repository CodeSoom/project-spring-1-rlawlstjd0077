package com.jinseong.soft.errors;

/**
 * 유저 not found 예외.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found - id: " + id);
    }

    public UserNotFoundException(String email) {
        super("User not found - email: " + email);
    }
}
