package com.car.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "model")
@Data
public class Model {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "maker_id")
	private Integer makerId;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "isDeleted", columnDefinition  = "boolean default 0")
	private boolean isDeleted;
	
	
}
