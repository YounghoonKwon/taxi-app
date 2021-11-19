package com.test.project.service;

import com.test.project.auth.JwtTokenProvider;
import com.test.project.domain.User;
import com.test.project.domain.UserType;
import com.test.project.dto.UserSigninRequest;
import com.test.project.dto.UserSignupRequest;
import com.test.project.exception.BadRequestException;
import com.test.project.exception.ConflictException;
import com.test.project.exception.UnAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
@DisplayName("유저 비지니스 흐름 테스트")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        userService.signup(new UserSignupRequest("test@gmail.com", "test", UserType.DRIVER));
    }

    @DisplayName("유저 로그인 테스트 - 성공")
    @Test
    void signin() {
        //given
        //when
        //then
        assertThatCode(() -> userService.signin(new UserSigninRequest("test@gmail.com", "test"))).doesNotThrowAnyException();
    }

    @DisplayName("유저 로그인 테스트 - 실패 - 아이디가 존재하지 않는 경우")
    @Test
    void signinFailsWhenDuplicateIdExists() {
        //given
        //when
        //then
        assertThatCode(() -> userService.signin(new UserSigninRequest("testnotexists@gmail.com", "test"))).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("유저 회원가입 테스트 - 성공")
    @Test
    void signup() {
        //given
        //when
        //then
        assertThatCode(() -> userService.signup(new UserSignupRequest("testnew@gmail.com", "test", UserType.DRIVER))).doesNotThrowAnyException();
    }

    @DisplayName("유저 회원가입 테스트 - 실패 - 이미 존재하는 유저가 있는 경우")
    @Test
    void signupFailsWhenAlreadyExists() {
        //given
        //when
        //then
        assertThatCode(() -> userService.signup(new UserSignupRequest("test@gmail.com", "test", UserType.DRIVER))).isInstanceOf(ConflictException.class);
    }

    @DisplayName("유저 토큰 검색 테스트")
    @Test
    void findByAccessToken() {
        //given
        //when
        given(jwtTokenProvider.getPayload("token")).willReturn("test@gmail.com");
        //then
        final User user = userService.findByAccessToken("token");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
    }

    @DisplayName("유저 토큰 검색 테스트 - 실패 - 유효하지 않은 토큰")
    @Test
    void findByAccessTokenFails() {
        //given
        //when
        //then
        assertThatCode(() -> userService.findByAccessToken("token")).isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("유저 토큰 검증 테스트")
    @Test
    void validateAccessToken() {
        //given
        //when
        given(jwtTokenProvider.validateToken("token")).willReturn(true);
        //then
        assertThatCode(() -> userService.validateAccessToken("token")).doesNotThrowAnyException();
    }

    @DisplayName("유저 토큰 검증 테스트 - 실패 - 유효하지 않은 토큰")
    @Test
    void validateAccessTokenFails() {
        //given
        //when
        //then
        assertThatCode(() -> userService.validateAccessToken("token")).isInstanceOf(UnAuthorizedException.class);
    }
}