package com.test.project.service;

import com.test.project.domain.Status;
import com.test.project.domain.User;
import com.test.project.domain.UserType;
import com.test.project.dto.DispatchRequest;
import com.test.project.dto.DispatchResponse;
import com.test.project.exception.ConflictException;
import com.test.project.exception.ForbiddenException;
import com.test.project.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("배차 비지니스 흐름 테스트")
class DispatchServiceTest {

    @Autowired
    private DispatchService dispatchService;

    @Autowired
    private UserRepository userRepository;

    private User passenger;
    private User driver;
    private DispatchRequest dispatchRequest;

    @BeforeEach
    void setUp() {
        passenger = User.builder()
            .email("testpassenger@gmail.com")
            .password("test")
            .userType(UserType.PASSENGER)
            .build();
        driver = User.builder()
            .email("testdriver@gmail.com")
            .password("test")
            .userType(UserType.DRIVER)
            .build();
        userRepository.saveAll(Arrays.asList(driver, passenger));

        dispatchRequest = new DispatchRequest("루터회관");
    }

    @Test
    @DisplayName("배차 생성 테스트 - 성공")
    void create() {
        //given
        //when
        //then
        assertThatCode(() -> dispatchService.create(passenger, dispatchRequest)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("배차 생성 테스트 - 실패 - 대기중인 요청이 있을 경우")
    void createFailsWhenPendingRequest() {
        //given
        //when
        dispatchRequest = new DispatchRequest("");
        final DispatchResponse dispatchResponse = dispatchService.create(passenger, dispatchRequest);
        //then
        assertThatThrownBy(() -> dispatchService.create(passenger, dispatchRequest))
            .isInstanceOf(ConflictException.class);
    }

    @Test
    @DisplayName("배차 생성 테스트 - 실패 - 운전자가 배차 요청한 경우")
    void createFailsWhenDriverRequested() {
        //given
        //when
        //then
        assertThatThrownBy(() -> dispatchService.create(driver, dispatchRequest))
            .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @DisplayName("배차 조회 테스트 - 성공")
    void findAllDispatches() {
        //given
        final DispatchResponse dispatchResponse = dispatchService.create(passenger, dispatchRequest);
        //when
        final List<DispatchResponse> dispatches = dispatchService.findAllDispatches(passenger.getId());
        //then
        assertThat(dispatches).extracting("passengerId").containsExactlyElementsOf(Collections.singleton(passenger.getId()));
        assertThat(dispatches).extracting("address").containsExactlyElementsOf(Collections.singleton("루터회관"));
        assertThat(dispatches).extracting("id").containsExactlyElementsOf(Collections.singleton(dispatchResponse.getId()));
        assertThat(dispatches).extracting("status").containsExactlyElementsOf(Collections.singleton(Status.STAND_BY));
    }

    @Test
    @DisplayName("배차 업데이트 테스트 - 성공")
    void update() {
        //given
        final DispatchResponse dispatchResponse = dispatchService.create(passenger, dispatchRequest);
        //when
        dispatchService.update(driver, dispatchResponse.getId());
        //then
        final List<DispatchResponse> dispatches = dispatchService.findAllDispatches(passenger.getId());
        final DispatchResponse dispatchResponseAfterUpdate = dispatches.get(0);
        assertThat(dispatchResponseAfterUpdate.getDriverId()).isEqualTo(driver.getId());
        assertThat(dispatchResponseAfterUpdate.getStatus()).isEqualTo(Status.ACCEPTED);
    }

    @Test
    @DisplayName("배차 업데이트 테스트 - 실패 - 기사가 아닌 다른 유저가 배차 요청한 경우")
    void updateFailsWhenPassengerAccepted() {
        //given
        final DispatchResponse dispatchResponse = dispatchService.create(passenger, dispatchRequest);
        //when
        //then
        assertThatCode(() -> dispatchService.update(passenger, dispatchResponse.getId())).isInstanceOf(ForbiddenException.class);
    }

    @Test
    @DisplayName("배차 업데이트 테스트 - 실패 - 이미 다른 기사에 의해 요청이 수락된 경우")
    void updateWhenAnotherDriverAlreadyAccepted() {
        //given
        final DispatchResponse dispatchResponse = dispatchService.create(passenger, dispatchRequest);
        dispatchService.update(driver, dispatchResponse.getId());
        //when
        //then
        assertThatCode(() -> dispatchService.update(driver, dispatchResponse.getId())).isInstanceOf(ConflictException.class);
    }
}