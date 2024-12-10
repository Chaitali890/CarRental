package com.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarSellApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSellApplication.class, args);
	}

}
