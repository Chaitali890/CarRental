package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.entity.LightBill;

public interface LightBillRepository extends JpaRepository<LightBill, Integer> {

	LightBill findFirstByIsDeletedFalse();

}
