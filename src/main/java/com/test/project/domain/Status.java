package com.test.project.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("standBy")
    STAND_BY,
    @JsonProperty("accepted")
    ACCEPTED
}
