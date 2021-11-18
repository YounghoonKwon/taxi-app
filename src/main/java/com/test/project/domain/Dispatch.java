package com.test.project.domain;

import lombok.*;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "dispatch_id"))
@ToString
public class Dispatch extends BaseEntity {

    private String address;
    private Integer driverId;
    private Integer passengerId;
    private Status status;
    private LocalDateTime acceptedAt;

    @Builder
    public Dispatch(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, String address, Integer driverId,
                    Integer passengerId, Status status, LocalDateTime acceptedAt) {
        super(id, createdAt, updatedAt);
        this.address = address;
        this.driverId = driverId;
        this.passengerId = passengerId;
        this.status = status;
        this.acceptedAt = acceptedAt;
    }

    public void update(Dispatch toChange) {
        this.driverId = toChange.getDriverId();
        this.status = toChange.getStatus();
        this.acceptedAt = toChange.getAcceptedAt();
    }

    public boolean isStandByStatus() {
        return this.status == Status.STAND_BY;
    }

    public boolean isAcceptedStatus(){
        return this.status == Status.ACCEPTED;
    }
}
