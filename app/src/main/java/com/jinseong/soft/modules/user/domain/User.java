package com.jinseong.soft.modules.user.domain;

import com.jinseong.soft.modules.commons.DateAudit;
import com.jinseong.soft.modules.user.dto.UserNameGettable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 유저 정보
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends DateAudit {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private boolean deleted = false;

    /**
     * 삭제 상태로 변경합니다.
     */
    public void destroy() {
        deleted = true;
    }

    public void changeWith(UserNameGettable source) {
        this.name = source.getName();
    }

    public void changePassword(String password,
                               PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public boolean authenticated(String password,
                                 PasswordEncoder encoder) {
        return encoder.matches(password, this.password);
    }
}
