package com.car.response;

import lombok.Data;

@Data
public class DashboardCountResponse {

	private Integer modelCount;
	
	private Integer makerCount;
	
	private Integer customerCount;
}
