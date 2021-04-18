package com.jinseong.soft.modules.user.application;

import com.jinseong.soft.modules.user.domain.User;
import com.jinseong.soft.modules.user.domain.UserEmailDuplicationException;
import com.jinseong.soft.modules.user.domain.UserNameNotFoundException;
import com.jinseong.soft.modules.user.domain.UserNotFoundException;
import com.jinseong.soft.modules.user.domain.UserRepository;
import com.jinseong.soft.modules.user.dto.UserRegistrationData;
import com.jinseong.soft.modules.user.dto.UserUpdateData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 유저에 대한 비즈니스 로직을 제공합니다.
 */
@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 주어진 유저 생성 정보로 유저를 생성한 뒤 반환합니다.
     *
     * @param userRegistrationData 유저 생성 정보
     * @return 생성된 유저 정보
     * @throws UserEmailDuplicationException 중복된 유저 email 정보가 주어진 경우
     */
    @Transactional
    public User registerUser(UserRegistrationData userRegistrationData) {
        String email = userRegistrationData.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationException(email);
        }

        User source = User.builder()
                .email(userRegistrationData.getEmail())
                .name(userRegistrationData.getName())
                .build();
        source.changePassword(userRegistrationData.getPassword(), passwordEncoder);

        return userRepository.save(source);
    }

    /**
     * 대응되는 식별자의 유저를 삭제한 뒤 반환합니다.
     *
     * @param id 유저 식별자
     * @return 삭제된 유저
     */
    @Transactional
    public User destroyUser(Long id) {
        User user = findUser(id);
        user.destroy();
        return user;
    }

    /**
     * 대응되는 식별자의 유저를 주어진 유저 정보로 수정한 뒤 반환합니다.
     *
     * @param id         유저 식별자
     * @param updateData 유저 수정 정보
     * @return 수정된 유저
     */
    @Transactional
    public User updateUser(Long id, UserUpdateData updateData) {
        User user = findUser(id);
        user.changeWith(updateData);

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

    /**
     * 전발답은 유저이름과 일치하는 유저를 반환합니다.
     *
     * @param username 유저 이름
     * @return 유저
     */
    public User findByUsername(String username) {
        return userRepository.findByNameAndDeletedIsFalse(username)
                .orElseThrow(() -> new UserNameNotFoundException(username));
    }

    /**
     * 존재하는 모든 유저의 이름을 반환합니다.
     *
     * @return 유저 이름 목록
     */
    public List<String> getUserNames() {
        return userRepository.findAll()
                .stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    /**
     * 주어진 유저 email과 일치하는 유저 인증 정보를 반환합니다.
     *
     * @param email 유저 email
     * @return 유저 인증 정보
     * @throws UsernameNotFoundException 주어진 유저 email과 일치하는 유저를 찾지 못한 경우
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to find username - " + email));

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return authorities;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
