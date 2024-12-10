package com.car.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserRequest {

	private Integer id;
	
	private String customerName;
	
	private String city;

	private String district;
	
	private String state;

	private Integer pincode;
	
	private Long contactNumber;
	
}
