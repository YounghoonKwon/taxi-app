package com.test.project.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DispatchRequest {

    @Size(min = 1, message = "1글자 이상")
    @Size(max = 100, message = "100글자 이하")
    private String address;

    public DispatchRequest(String address) {
        this.address = address;
    }
}
