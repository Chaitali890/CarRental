package com.car.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReturnCarResponse {

	private String carId;
	
	private String custId;
	
	private LocalDate date;

	private LocalDate dueDate;
	
	private Integer daysElapsed;
	
	private Long fine;
}
