package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.CarBuy;

@Repository
public interface CarBuyRepository extends JpaRepository<CarBuy,Integer> {

}
