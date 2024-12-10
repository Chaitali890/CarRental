package com.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.entity.Maker;

@Repository
public interface MakerRepository extends JpaRepository<Maker,Integer> {

}
