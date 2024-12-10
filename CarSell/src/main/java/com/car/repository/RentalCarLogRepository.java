package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.RentalCarLog;

@Repository
public interface RentalCarLogRepository extends JpaRepository<RentalCarLog, Integer> {

}
