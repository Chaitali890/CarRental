package com.car.request;

import lombok.Data;

@Data
public class AddModelRequest {

	private Integer id;
	
	private Integer makerId;
	
	private String model;
}
