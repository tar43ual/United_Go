package com.ual.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ual.dto.BookingDTO;
import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;
import com.ual.service.BookingServiceImpl;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/urs")
@Validated
public class BookingLogicController {
	
	@Autowired
	private BookingServiceImpl bookingService;
	
	
	
	@GetMapping(value="/greet")
	public ResponseEntity<String> greet(){
		String msg = "welcome to United Go!";
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@GetMapping(value="/flights/{source}/{destination}/{date}")
	public ResponseEntity<List<FlightDTO>>searchFlights( @Pattern(regexp = "^[a-zA-Z]+$",message="{Validator.INVALID_SOURCE}")@PathVariable String source,
			 @Pattern(regexp = "^[a-zA-Z]+$",message="{Validator.INVALID_DESTINATION}")@PathVariable String destination,
			 @FutureOrPresent(message="{Validator.INVALID_DATE}")@PathVariable LocalDate date ) throws UnitedGoException{
		
		if(source.isBlank() || source.equals(" ")) {
			throw new UnitedGoException("Validator.INVALID_SOURCE");
		}else if(destination.isBlank() || destination.equals(" ")) {
			throw new UnitedGoException("Validator.INVALID_DESTINATION");
		}else if(date == null) {
			throw new UnitedGoException("Validator.INVALID_DATE");
		}
		
		List<FlightDTO> list = bookingService.searchFlights(source,destination,date);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping(value="/flights/book")
	public ResponseEntity<String> bookFlight(@Min(value=1, message="{Validator.INVALID_PAX}") @Max(value=4,message="{Validator.INVALID_PAX}")@RequestParam Integer noOfPassengers,@Pattern(regexp = "^[A-Z]{2}-[0-9]{3}$", message = "{Validator.INVALID_FLIGHT_ID}") @RequestParam String flightId) throws UnitedGoException{
		
		String pnr = bookingService.addBooking(noOfPassengers,flightId);
		String msg = "Your tickets have been confirmed on the Indigo flight with flightId : "+flightId + " and your pnr is : "+ pnr; 
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/bookings/{pnr}")
	public ResponseEntity<BookingDTO>searchBooking(@Pattern(regexp = "^[A-Z]{3}[0-9]{3}$$", message = "{Validator.INVALID_PNR}")@PathVariable String pnr) throws UnitedGoException {
		BookingDTO bookingDTO = bookingService.searchBookingByPnr(pnr);	
		return new ResponseEntity<>(bookingDTO,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/bookings/{pnr}/cancel")
	public ResponseEntity<String> cancelBooking(@Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "{Validator.INVALID_PNR}")@PathVariable String pnr) throws UnitedGoException {
		String msg = bookingService.deleteBooking(pnr);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
}
