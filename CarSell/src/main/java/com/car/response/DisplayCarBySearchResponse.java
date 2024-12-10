package com.car.response;

import java.util.List;

import lombok.Data;

@Data
public class DisplayCarBySearchResponse {

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
