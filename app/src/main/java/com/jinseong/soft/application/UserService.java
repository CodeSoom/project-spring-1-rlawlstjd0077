package com.jinseong.soft.application;

import com.jinseong.soft.domain.User;
import com.jinseong.soft.domain.UserRepository;
import com.jinseong.soft.dto.UserRegistrationData;
import com.jinseong.soft.dto.UserUpdateData;
import com.jinseong.soft.errors.UserEmailDuplicationException;
import com.jinseong.soft.errors.UserNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(UserRegistrationData registrationData) {
        String email = registrationData.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationException(email);
        }

        User source = User.builder()
                .email(registrationData.getEmail())
                .name(registrationData.getName())
                .build();
        source.changePassword(registrationData.getPassword(), passwordEncoder);

        return userRepository.save(source);
    }

    @Transactional
    public User destroyUser(Long id) {
        User user = findUser(id);
        user.destroy();
        return user;
    }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to find username - " + username));

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
