package com.car.request;

import java.util.List;

import com.car.entity.CarBuy;

import lombok.Data;

@Data
public class AddCarBuyRequest {
	
	private Integer id;
	
	private Integer carBuyId;
	
	private Integer makeId;
	
	private List<Integer> modelIds;

	private String transmission;
	
	private String color;

	
}
