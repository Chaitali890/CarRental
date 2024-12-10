package com.car.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.car.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

	@Query(value = "select cust_identification_number from user",nativeQuery = true)
	List<String> findByCustId();

	
}
