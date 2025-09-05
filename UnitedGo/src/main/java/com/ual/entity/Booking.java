package com.ual.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class Booking {
	
	@Id
	private String pnr;
	
	private Integer noOfPassengers;
	
	private double totalFare;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="flight")// this column name should be same as in booking table
	private Flight flight;

	
	
	
}
