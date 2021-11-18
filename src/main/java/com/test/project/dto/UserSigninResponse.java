package com.test.project.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSigninResponse {

    private String accessToken;

    private UserSigninResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static UserSigninResponse from(String accessToken) {
        return new UserSigninResponse(accessToken);
    }
}
