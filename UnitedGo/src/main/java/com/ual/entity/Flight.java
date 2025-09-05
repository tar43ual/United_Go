package com.ual.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
	
	@Id
//	@Column(name="flight_id")
	private String flightId;
	
	private String airline;
	
	private String source;
	
	private String destination;
	
	private String arrivalTime;
	
	private String departureTime;
	
	private Integer availableSeats;
	
	private LocalDate availableDate;
	
	private double fare;
	
}
