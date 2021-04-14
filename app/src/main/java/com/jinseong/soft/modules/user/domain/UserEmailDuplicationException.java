package com.jinseong.soft.modules.user.domain;

/**
 * 유저 email 중복 예외.
 */
public class UserEmailDuplicationException extends RuntimeException {
    public UserEmailDuplicationException(String email) {
        super("User email is alreay existed: " + email);
    }
}
