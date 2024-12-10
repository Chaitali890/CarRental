package com.car.request;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RentalCarRequest {
	

	private Integer id;
	
	private String carId;
	
	private String custId;
	
	private String custName;
	
	private Integer rentalFees;
	
	private LocalDate date;

	private LocalDate dueDate;
}
