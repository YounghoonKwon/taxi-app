package com.test.project.dto;

import com.test.project.domain.User;
import com.test.project.domain.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRequest {

    @Email
    private String email;

    @Size(min = 1, message = "1글자 이상")
    @Size(max = 15, message = "15글자 이하")
    private String password;
    private UserType userType;

    public UserSignupRequest(String email, String password, UserType userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public User toEntity() {
        return User.builder()
            .email(email)
            .password(password)
            .userType(userType)
            .build();
    }
}
