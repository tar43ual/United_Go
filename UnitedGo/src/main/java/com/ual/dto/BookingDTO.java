package com.ual.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
	
	private String pnr;
	
	private Integer noOfPassengers;

	private double totalFare;
	
	private FlightDTO flight;

	public BookingDTO(Integer noOfPassengers, FlightDTO flight) {
		super();
		this.noOfPassengers = noOfPassengers;
		this.flight = flight;
	}
	
	
}
