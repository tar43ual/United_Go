package com.ual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;
import com.ual.service.FlightServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/urs")
@Validated
public class FlightLogicController {
	
	@Autowired
	private FlightServiceImpl flightService;
	
	@GetMapping(value="/greeting")
	public ResponseEntity<String> greet(){
		String msg = "welcome to United Go!";
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@PostMapping("/addflight")
	public ResponseEntity<String> addFlight(@Valid @RequestBody FlightDTO flightDTO) throws UnitedGoException {
		flightService.addFlight(flightDTO);
		String msg = "Flight added successfully";
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
}
