package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.RechargePlan;

@Repository
public interface RechargePlanRepository extends JpaRepository<RechargePlan, Integer> {

}
