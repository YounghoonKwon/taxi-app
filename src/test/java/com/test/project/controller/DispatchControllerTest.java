package com.test.project.controller;

import com.test.project.auth.JwtTokenProvider;
import com.test.project.domain.Status;
import com.test.project.domain.User;
import com.test.project.domain.UserType;
import com.test.project.dto.DispatchRequest;
import com.test.project.dto.DispatchResponse;
import com.test.project.service.DispatchService;
import com.test.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("배차 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = DispatchController.class)
class DispatchControllerTest extends ApiDocument {

    private static final String ACCESS_TOKEN = "{ACCESS TOKEN}";
    @MockBean
    private DispatchService dispatchService;

    @MockBean
    private UserService userService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    private User driver;

    @BeforeEach
    void setUp() {
        driver = User.builder()
            .id(1)
            .email("testdriver@gmail.com")
            .password("test")
            .userType(UserType.DRIVER)
            .build();
        given(userService.findByAccessToken(ACCESS_TOKEN)).willReturn(driver);
    }

    @Test
    @DisplayName("배차 조회 테스트")
    void findAll() throws Exception {
        //given
        List<DispatchResponse> dispatchResponses = new ArrayList<>(Arrays.asList(
            new DispatchResponse(1, "큰집", 2, 1, Status.ACCEPTED, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()),
            new DispatchResponse(2, "작은집", null, 1, Status.STAND_BY, null, LocalDateTime.now(), LocalDateTime.now())
        ));
        //when
        given(dispatchService.findAllDispatches(any())).willReturn(dispatchResponses);

        //then
        final ResultActions resultActions = mockMvc.perform(get("/taxi-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "TOKEN" + ACCESS_TOKEN));

        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(toDocument("dispatch-find"));
    }

    @Test
    @DisplayName("배차 생성 테스트")
    void create() throws Exception {
        //given
        final DispatchRequest dispatchRequest = new DispatchRequest("큰집");
        final DispatchResponse dispatchResponse = new DispatchResponse(2, "작은집", null, 1, Status.STAND_BY, null, LocalDateTime.now(), LocalDateTime.now());
        given(dispatchService.create(any(), any())).willReturn(dispatchResponse);
        //when
        final ResultActions resultActions = mockMvc.perform(post("/taxi-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(dispatchRequest))
            .header(HttpHeaders.AUTHORIZATION, "TOKEN" + ACCESS_TOKEN));
        //then
        resultActions.andExpect(status().isCreated())
            .andExpect(content().json(toJson(dispatchResponse)))
            .andDo(print())
            .andDo(toDocument("dispatch-create"));
    }

    @Test
    @DisplayName("배차 수정 테스트")
    void update() throws Exception {
        //given
        final DispatchResponse dispatchResponse = new DispatchResponse(2, "작은집", 1, 2, Status.ACCEPTED, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        given(dispatchService.update(any(), anyInt())).willReturn(dispatchResponse);
        //when
        final ResultActions resultActions = mockMvc.perform(put("/taxi-requests/{taxiRequestId}/accept", 2));
        //then
        resultActions.andExpect(status().isOk())
            .andExpect(content().json(toJson(dispatchResponse)))
            .andDo(print())
            .andDo(toDocument("dispatch-update"));
    }
}