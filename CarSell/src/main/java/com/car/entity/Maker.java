package com.car.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "maker")
@Data
public class Maker {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "maker")
	private String maker;
	
	@Column(name = "isDeleted", columnDefinition = "boolean default 0")
	private Boolean isDeleted = false;
	
	
	
}
