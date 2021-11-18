package com.test.project.repository;

import com.test.project.domain.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispatchRepository extends JpaRepository<Dispatch, Integer> {

    List<Dispatch> findAllByPassengerId(Integer passengerId);
}
