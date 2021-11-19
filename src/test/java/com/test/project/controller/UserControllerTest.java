package com.test.project.controller;

import com.test.project.domain.UserType;
import com.test.project.dto.UserSigninRequest;
import com.test.project.dto.UserSigninResponse;
import com.test.project.dto.UserSignupRequest;
import com.test.project.dto.UserSignupResponse;
import com.test.project.exception.BadRequestException;
import com.test.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("유저 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends ApiDocument {

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("유저 로그인 테스트 - 성공")
    void signin() throws Exception {
        //given
        final UserSigninResponse userSigninResponse = UserSigninResponse.from("AccessToken");
        given(userService.signin(any(UserSigninRequest.class))).willReturn(userSigninResponse);
        //when
        final UserSigninRequest userSigninRequest = new UserSigninRequest("email@email.com", "password");
        final ResultActions resultActions = 로그인_요청(userSigninRequest);
        //then
        로그인_성공(userSigninResponse, resultActions);
    }

    @Test
    @DisplayName("유저 로그인 테스트 - 성공")
    void signinFails() throws Exception {
        //given
        given(userService.signin(any(UserSigninRequest.class))).willThrow(BadRequestException.class);
        //when
        final UserSigninRequest userSigninRequest = new UserSigninRequest("email@email.com", "password");
        final ResultActions resultActions = 로그인_요청(userSigninRequest);
        //then
        로그인_실패(resultActions);
    }

    private ResultActions 로그인_요청(UserSigninRequest userSigninRequest) throws Exception {
        return mockMvc.perform(post("/users/sign-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(userSigninRequest)));
    }

    private void 로그인_성공(UserSigninResponse userSigninResponse, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
            .andExpect(content().json(toJson(userSigninResponse)))
            .andDo(print())
            .andDo(toDocument("user-signin"));
    }

    private void 로그인_실패(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
            .andDo(toDocument("user-signin-fail"));
    }

    @Test
    @DisplayName("유저 회원가입 테스트 - 성공")
    void signup() throws Exception {
        //given
        final UserSignupRequest userSignupRequest = new UserSignupRequest("test@gmail.com", "password", UserType.DRIVER);
        final UserSignupResponse userSignupResponse = new UserSignupResponse("test@gmail.com", 1, UserType.DRIVER, LocalDateTime.now(), LocalDateTime.now());
        given(userService.signup(any(UserSignupRequest.class))).willReturn(userSignupResponse);
        //when
        final ResultActions resultActions = 회원가입_요청(userSignupRequest);
        //then
        회원가입_성공(userSignupResponse, resultActions);
    }

    @Test
    @DisplayName("유저 회원가입 테스트 - 실패")
    void signupFails() throws Exception {
        //given
        final UserSignupRequest userSignupRequest = new UserSignupRequest("It'sNotValidEmail", "password", UserType.DRIVER);
        given(userService.signup(userSignupRequest)).willThrow(BadRequestException.class);
        //when
        final ResultActions resultActions = 회원가입_요청(userSignupRequest);
        //then
        회원가입_실패(resultActions);
    }

    private ResultActions 회원가입_요청(UserSignupRequest userSignupRequest) throws Exception {
        return mockMvc.perform(post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(userSignupRequest)));
    }

    private void 회원가입_성공(UserSignupResponse userSignupResponse, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isCreated())
            .andExpect(content().json(toJson(userSignupResponse)))
            .andDo(print())
            .andDo(toDocument("user-signup"));
    }

    private void 회원가입_실패(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
            .andDo(toDocument("user-signup-fail"));
    }
}