package com.jinseong.soft.errors;

/**
 * 유 not found 예외.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found - id: " + id);
    }
}
