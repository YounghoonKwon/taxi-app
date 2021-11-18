package com.test.project.controller;

import com.test.project.dto.UserSigninRequest;
import com.test.project.dto.UserSigninResponse;
import com.test.project.dto.UserSignupRequest;
import com.test.project.dto.UserSignupResponse;
import com.test.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public UserSigninResponse signin(@RequestBody UserSigninRequest userSigninRequest) {
        return userService.signin(userSigninRequest);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignupResponse signup(@RequestBody UserSignupRequest userSignupRequest) {
        return userService.signup(userSignupRequest);
    }
}
