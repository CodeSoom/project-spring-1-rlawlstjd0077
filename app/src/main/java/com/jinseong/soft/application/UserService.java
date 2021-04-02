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
        User user = findUser(id);
        user.destroy();
        return user;
    }

    /**
     * 전달받은 id와 일치하는 유저를 반환합니다.
     *
     * @param id 유저 식별자
     * @return 유저
     */
    private User findUser(Long id) {
        return userRepository.findByIdAndDeletedIsFalse(id);
    }
}
