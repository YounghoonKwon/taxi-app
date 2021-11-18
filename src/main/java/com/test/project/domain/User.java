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
    public User(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String email, String password, UserType userType) {
        super(id, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}
