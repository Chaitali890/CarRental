package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.LightRate;

@Repository
public interface LightRateRepository extends JpaRepository<LightRate, Integer> {

	LightRate findFirstByIsDeletedFalse();

}
