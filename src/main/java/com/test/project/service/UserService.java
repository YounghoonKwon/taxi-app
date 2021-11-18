package com.test.project.service;

import com.test.project.auth.JwtTokenProvider;
import com.test.project.domain.User;
import com.test.project.dto.UserSigninRequest;
import com.test.project.dto.UserSigninResponse;
import com.test.project.dto.UserSignupRequest;
import com.test.project.dto.UserSignupResponse;
import com.test.project.exception.DuplicateException;
import com.test.project.exception.UnAuthorizedException;
import com.test.project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserSigninResponse signin(UserSigninRequest userSigninRequest) {
        validateUser(userSigninRequest);
        return UserSigninResponse.from(jwtTokenProvider.createToken(userSigninRequest.getEmail()));
    }

    private void validateUser(UserSigninRequest userSigninRequest) {
        final boolean existance = userRepository.existsByEmailAndPassword(userSigninRequest.getEmail(), userSigninRequest.getPassword());
        if (!existance) {
            throw new UnAuthorizedException("유저 정보를 찾을 수 없습니다.");
        }
    }

    public UserSignupResponse signup(UserSignupRequest userSignupRequest) {
        validateDuplicate(userSignupRequest);
        final User user = userRepository.save(userSignupRequest.toEntity());
        return UserSignupResponse.of(user);
    }

    private void validateDuplicate(UserSignupRequest userSignupRequest) {
        final boolean existance = userRepository.existsByEmail(userSignupRequest.getEmail());
        if (existance) {
            throw new DuplicateException("이미 존재하는 유저가 있습니다");
        }
    }

    public User findByAccessToken(String accessToken) {
        String email = String.valueOf(jwtTokenProvider.getPayload(accessToken));
        return userRepository.findByEmail(email);
    }

    public void validateAccessToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new UnAuthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
