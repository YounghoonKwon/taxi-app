package com.test.project.dto;

import com.test.project.domain.Dispatch;
import com.test.project.domain.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DispatchResponse {

    private Integer id;
    private String address;
    private Integer driverId;
    private Integer passengerId;
    private Status status;
    private LocalDateTime acceptedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DispatchResponse(Integer id, String address, Integer driverId, Integer passengerId, Status status, LocalDateTime acceptedAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.address = address;
        this.driverId = driverId;
        this.passengerId = passengerId;
        this.status = status;
        this.acceptedAt = acceptedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static DispatchResponse from(Dispatch dispatch) {
        return new DispatchResponse(dispatch.getId(), dispatch.getAddress(), dispatch.getDriverId(), dispatch.getPassengerId(), dispatch.getStatus(), dispatch.getAcceptedAt(), dispatch.getCreatedAt(), dispatch.getUpdatedAt());
    }

    public static List<DispatchResponse> from(List<Dispatch> dispatches) {
        return dispatches.stream()
            .map(DispatchResponse::from)
            .collect(Collectors.toList());
    }
}
