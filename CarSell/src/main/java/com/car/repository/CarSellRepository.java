package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.CarSell;

@Repository
public interface CarSellRepository extends JpaRepository<CarSell,Integer> {

}
