package com.car.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.car.entity.CarRegister;
import com.car.entity.RentalCar;

@Repository
public interface CarRentalRepository extends JpaRepository<RentalCar,Integer> {

	@Query(value = "select * from rental_car where car_id = :carId", nativeQuery = true)
	Optional<RentalCar> findByCarId(@Param("carId") String carId);
	
	@Query(value = "select * from rental_car where cust_id = :custId", nativeQuery = true)
	Optional<RentalCar> findByCustId(@Param("custId") String custId);

	@Query(value = "SELECT DISTINCT make FROM car_db", nativeQuery = true)
	List<String> findByMake();

	@Query(value = "select distinct model from car_db",nativeQuery = true)
	List<String> findByModel();


	@Query(value="select * from rental_car where car_id = :identificationNumber", nativeQuery = true)
	List<RentalCar> findByCarIds(String identificationNumber);


}
