package com.test.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("배차 단위 테스트")
class DispatchTest {

    private Dispatch dispatch;
    private Dispatch updateDispatch;
    private LocalDateTime localDateTime;

    @BeforeEach
    void setup() {
        dispatch = Dispatch.builder()
            .id(1)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .address("루터회관")
            .passengerId(1)
            .status(Status.STAND_BY)
            .build();
        localDateTime = LocalDateTime.now();
        updateDispatch = Dispatch.builder()
            .id(1)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .address("루터회관")
            .passengerId(1)
            .driverId(2)
            .status(Status.ACCEPTED)
            .acceptedAt(localDateTime)
            .build();
    }

    @Test
    @DisplayName("배차 update 테스트")
    void update() {
        //given
        //when
        dispatch.update(updateDispatch);
        //then
        assertThat(dispatch.getAcceptedAt()).isEqualTo(updateDispatch.getAcceptedAt());
        assertThat(dispatch.getStatus()).isEqualTo(updateDispatch.getStatus());
        assertThat(dispatch.getDriverId()).isEqualTo(updateDispatch.getDriverId());
    }

    @Test
    @DisplayName("배차 isStandByStatus 테스트")
    void isStandByStatus() {
        //given
        //when
        //then
        assertThat(dispatch.isStandByStatus()).isTrue();
    }

    @Test
    @DisplayName("배차 isAcceptedStatus 테스트")
    void isAcceptedStatus() {
        //given
        //when
        //then
        assertThat(updateDispatch.isAcceptedStatus()).isTrue();
    }

    @Test
    @DisplayName("배차 getAddress 테스트")
    void getAddress() {
        //given
        //when
        //then
        assertThat(dispatch.getAddress()).isEqualTo("루터회관");
    }

    @Test
    @DisplayName("배차 getDriverId 테스트")
    void getDriverId() {
        //given
        //when
        //then
        assertThat(updateDispatch.getDriverId()).isEqualTo(2);
    }

    @Test
    @DisplayName("배차 getPassengerId 테스트")
    void getPassengerId() {
        //given
        //when
        //then
        assertThat(dispatch.getPassengerId()).isEqualTo(1);
    }

    @Test
    @DisplayName("배차 getStatus 테스트")
    void getStatus() {
        //given
        //when
        //then
        assertThat(dispatch.getStatus()).isEqualTo(Status.STAND_BY);
    }

    @Test
    @DisplayName("배차 getAcceptedAt 테스트")
    void getAcceptedAt() {
        //given
        //when
        //then
        assertThat(updateDispatch.getAcceptedAt()).isEqualTo(localDateTime);
    }
}