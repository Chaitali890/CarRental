package com.car.request;

import java.util.List;

import lombok.Data;

@Data
public class AddReturnCarRequest {

	private String carId;
	
	private String custId;
	
	private String make;
	
	private String model;
	
	private List<String> color;
	
	private String transmission;
	
	private List<String> type;
	
	private Integer id;
	
	private Boolean isDeleted;

}
