package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.ChangePlan;

@Repository
public interface ChangePlanRepository extends JpaRepository<ChangePlan, Integer> {

}
