package com.test.project.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSigninRequest {

    @Email
    private String email;

    @Size(min = 1, message = "1글자 이상")
    @Size(max = 15, message = "15글자 이하")
    private String password;

    public UserSigninRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
