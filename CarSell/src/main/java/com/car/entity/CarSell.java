package com.car.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "car_buy")
@Data
public class CarSell {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "make_id")
	private Integer makeId;
	
	@Column(name = "model_id")
	private Integer modelId;
	
	@Column(name = "car_register_id")
	private Integer carBuyId;
	
	@Column(name = "isDeleted", columnDefinition = "boolean default 0")
	private Boolean isDeleted;
	
}
