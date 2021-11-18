package com.test.project.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DispatchRequest {
    private String address;

    public DispatchRequest(String address) {
        this.address = address;
    }
}
