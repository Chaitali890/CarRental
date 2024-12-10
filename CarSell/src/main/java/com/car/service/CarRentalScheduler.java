package com.car.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.car.repository.CarRentalRepository;

@Component
public class CarRentalScheduler {

	@Autowired
	private CarRentalRepository carRentalRepository;
	
	@Scheduled(cron = "0 */5 * * * ?") // Every hour
	public void perFormCarRentalTask() {
		System.out.println("Running Car Rental Task");
		
		carRentalRepository.findAll().forEach(car -> {
			System.out.println("Customer Name:"+car.getCustName() + " Car ID:" + car.getCarId());
		});
	}
	
	
	
}
