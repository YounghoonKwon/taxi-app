package com.test.project.controller;

import com.test.project.service.DispatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("배차 컨트롤러 Mock 테스트")
@WebMvcTest(controllers = DispatchControllerTest.class)
class DispatchControllerTest extends ApiDocument{

    @MockBean
    private DispatchService dispatchService;

    @Test
    @DisplayName("배차 조회 테스트")
    void findAll() {
    }

    @Test
    @DisplayName("배차 생성 테스트")
    void create() {
    }

    @Test
    @DisplayName("배차 수정 테스트")
    void update() {
    }
}