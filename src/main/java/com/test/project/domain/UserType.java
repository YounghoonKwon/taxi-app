package com.test.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserType {
    @JsonProperty("passenger")
    PASSENGER,
    @JsonProperty("driver")
    DRIVER
}
