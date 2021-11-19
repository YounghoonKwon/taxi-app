package com.test.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 단위 테스트")
class UserTest {

    private User driver;
    private User passenger;

    @BeforeEach
    void setUp() {
        driver = User.builder()
            .email("testdriver@gmail.com")
            .password("test")
            .userType(UserType.DRIVER)
            .build();

        passenger = User.builder()
            .email("testpassenger@gmail.com")
            .password("test")
            .userType(UserType.PASSENGER)
            .build();
    }

    @Test
    @DisplayName("유저 isPassenger 테스트")
    void isPassenger() {
        //given
        //when
        //then
        assertThat(passenger.isPassenger()).isTrue();
    }

    @Test
    @DisplayName("유저 isDriver 테스트")
    void isDriver() {
        //given
        //when
        //then
        assertThat(driver.isDriver()).isTrue();
    }

    @Test
    @DisplayName("유저 getEmail 테스트")
    void getEmail() {
        //given
        //when
        //then
        assertThat(passenger.getEmail()).isEqualTo("testpassenger@gmail.com");
    }

    @Test
    @DisplayName("유저 getPassword 테스트")
    void getPassword() {
        //given
        //when
        //then
        assertThat(passenger.getPassword()).isEqualTo("test");
    }

    @Test
    @DisplayName("유저 getUserType 테스트")
    void getUserType() {
        //given
        //when
        //then
        assertThat(passenger.getUserType()).isEqualTo(UserType.PASSENGER);
    }
}