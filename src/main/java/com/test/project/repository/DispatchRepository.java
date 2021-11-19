package com.test.project.repository;

import com.test.project.domain.Dispatch;
import com.test.project.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispatchRepository extends JpaRepository<Dispatch, Integer> {

    List<Dispatch> findAllByPassengerId(Integer passengerId);

    boolean existsByPassengerIdAndStatus(Integer passengerId, Status status);
}
