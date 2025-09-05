package com.ual.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class FlightDTO {
	
	@Pattern(regexp = "^[A-Z]{2}-\\d{3}$",message="FlightId should have first two letters to be uppercase alphabet separated by hypen and followed by 3 digits")
	@NotNull
	private String flightId;

	private String airline;

	@Pattern(regexp = "^[a-zA-Z]+$",message="Source attribute can only have uppercase and lowercase alphabets and cannot be empty")
	@NotNull
	private String source;

	@Pattern(regexp = "^[a-zA-Z]+$",message="destination attribute can only have uppercase and lowercase alphabets and cannot be empty")
	@NotNull
	private String destination;
	
	private String arrivalTime;

	private String departureTime;

	private Integer availableSeats;
	
	@FutureOrPresent(message="{Validator.INVALID_DATE}")
	private LocalDate availableDate;

	private double fare;
}
