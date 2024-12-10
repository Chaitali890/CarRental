package com.car.request;

import java.util.List;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CarRegisterRequest {

	private Integer id;

	private String make;

	private String model;

	private Boolean available;
	
	private List<String> type;
	
	private String transmission;
	
	private List<String> color;
	
	private String modelYear;
	
	private Double price;
	
	private String description;
}
