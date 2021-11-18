package com.test.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseEntity {

    private String email;
    private String password;
    private UserType userType;

    @Builder
    public User(Long id, LocalDateTime createdAt, String email, String password, UserType userType) {
        super(id, createdAt);
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}
