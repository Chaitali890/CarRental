package com.car.request;

import lombok.Data;

@Data
public class AddChangePlanRequest {

	private Integer id;

	private Integer newYear;
	
	private Integer rechargePlanId;
}

