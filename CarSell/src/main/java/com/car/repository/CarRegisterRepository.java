package com.car.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.car.entity.CarRegister;
import com.car.request.AddReturnCarRequest;

@Repository
public interface CarRegisterRepository extends JpaRepository<CarRegister,Integer> {

	@Query(value = "SELECT DISTINCT car_unique_number FROM car_db", nativeQuery = true)
	List<String> findAllUniqueIdentificationNumbers();

	@Query(value = "select * from car_db where make = :make", nativeQuery = true)
	List<CarRegister> findByMake(@Param("make") String make);
	


	@Query(value = "SELECT * FROM car_db WHERE make = :make OR transmission=:transmission", nativeQuery = true)
	List<CarRegister> findByMakeAndTransmission(
	    @Param("make") String make,
	    @Param("transmission") String transmission);
	

}
