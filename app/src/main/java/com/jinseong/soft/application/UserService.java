package com.jinseong.soft.application;

import com.jinseong.soft.domain.User;
import com.jinseong.soft.domain.UserRepository;
import com.jinseong.soft.dto.UserRegistrationData;
import com.jinseong.soft.errors.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(UserRegistrationData registrationData) {
        User source = User.builder()
                .email(registrationData.getEmail())
                .password(registrationData.getPassword())
                .name(registrationData.getName())
                .build();
        return userRepository.save(source);
    }

    @Transactional
    public User deleteUser(Long id) {
        User user = findUser(id);
        user.destroy();
        return user;
    }

    @Transactional
    public User updateUser(Long givenUserId, User source) {
        User user = findUser(givenUserId);
        user.changeWith(source);
        return user;
    }

    /**
     * 전달받은 id와 일치하는 유저를 반환합니다.
     *
     * @param id 유저 식별자
     * @return 유저
     */
    private User findUser(Long id) {
        return userRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
