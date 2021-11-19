package com.test.project.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@ToString
public class User extends BaseEntity {

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Builder
    public User(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String email, String password, UserType userType) {
        super(id, createdAt, updatedAt);
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public boolean isPassenger() {
        return this.userType == UserType.PASSENGER;
    }

    public boolean isDriver(){
        return this.userType == UserType.DRIVER;
    }
}
