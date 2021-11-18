package com.test.project.dto;

import com.test.project.domain.User;
import com.test.project.domain.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRequest {

    private String email;
    private String password;
    private UserType userType;

    public User toEntity() {
        return User.builder()
            .email(email)
            .password(password)
            .userType(userType)
            .build();
    }
}
