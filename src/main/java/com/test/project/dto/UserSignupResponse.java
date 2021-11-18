package com.test.project.dto;

import com.test.project.domain.User;
import com.test.project.domain.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupResponse {

    private String email;
    private Integer id;
    private UserType userType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserSignupResponse(String email, Integer id, UserType userType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.id = id;
        this.userType = userType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static UserSignupResponse of(User user) {
        return new UserSignupResponse(user.getEmail(), user.getId(), user.getUserType(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
