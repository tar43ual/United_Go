package com.ual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ual.dto.BookingDTO;
import com.ual.dto.FlightDTO;
import com.ual.entity.Flight;
import com.ual.exception.UnitedGoException;
import com.ual.repository.FlightRepository;
import com.ual.service.BookingServiceImpl;
import com.ual.service.FlightServiceImpl;

@SpringBootTest
class UnitedGoApplicationTests {
	
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private BookingServiceImpl bookingService;
	
	@Autowired
	private FlightServiceImpl flightService;

	@Test
	void contextLoads() {
	}
	
//	@Test
//	void searchFlightValidTest() {
//		
//		String source = "Delhi";
//		String destination = "Mumbai";
//		LocalDate date = LocalDate.of(2025, 8, 13);
//		
//		List<Flight> list = flightRepository.findFlights(source, destination, date);
//		
//		assertEquals(source, list.get(0).getSource());
//		assertEquals(destination, list.get(0).getDestination());
//		assertEquals(date, list.get(0).getAvailableDate());
//		
//	}
	
	@Test
	void searchFlightInvalidTest() {
		
		String source = "Delhi";
		String destination = "Chennai";
		LocalDate date = LocalDate.of(2025, 8, 15);
		
		List<Flight> list = flightRepository.findFlights(source, destination, date);
		
		//No flights should be found for this combination
		assertEquals(0, list.size());
		
	}
	
	
//	@Test
//	void bookFlightValidTestForFlightId() {
//		
//		String flightId = "IG-124";
//		Optional<Flight> optional = flightRepository.findById(flightId);
//		assertEquals(true, optional.isPresent());
//		
//	}
	
	@Test
	void bookFlightInvalidTestForFlightId() {
		
		String flightId = "IAG-9996"; 
		Optional<Flight> optional = flightRepository.findById(flightId);
		assertEquals(false, optional.isPresent());
		
	}
	
	@Test
	void bookFlightForInvalidRoute() {
		String source = "China";
		String destination = "Mumbai";
		LocalDate date = LocalDate.of(2025, 8, 12);
		
		
		
		FlightDTO flightDTO = new FlightDTO("IG-125","Indigo","Delhi","Mumbai","06:15:00","10:00:00",250,date,10000.00);
		
		
		UnitedGoException exception = assertThrows(UnitedGoException.class,()-> bookingService.searchFlights(source, destination, date));
        assertEquals("Service.NO_FLIGHTS_FOUND", exception.getMessage());
		
	}
	
	
//	@Test
//	public void testBookFlightValid() throws UnitedGoException {
//		
//			String flightId = "IG-124";
//			Integer noOfPax = 3;
//			
//			String response = bookingService.addBooking(noOfPax, flightId);
//			
//	        // Assert
//			Pattern pattern = Pattern.compile("URS[0-9]{3}");
//		    Matcher matcher = pattern.matcher(response);
//			
//	        assertTrue(matcher.find(),"Pattern Not Found in the response");
//	}
	
	@Test
	public void testBookFlightInValid() throws UnitedGoException {
		
		String flightId = "AB-103";
		Integer noOfPax = 3;
		
//		String response = bookingService.bookFlight(noOfPax, flightId);
 
        UnitedGoException exception = assertThrows(UnitedGoException.class,()-> bookingService.addBooking(noOfPax, flightId));
        assertEquals("Service.INVALID_FLIGHT_ID", exception.getMessage());
	}
	
 
	
	
//	@Test
//	public void testViewBookingValid() throws UnitedGoException{
//		String pnr = "URS983";
//		
//		
//		BookingDTO booking = bookingService.searchBookingByPnr(pnr);
//		
//		assertEquals(pnr,booking.getPnr());
//	}
//	
	@Test
	public void testViewBookingInValid() throws UnitedGoException{ 
		String pnr = "A1BC002";
		
		UnitedGoException exception = assertThrows(UnitedGoException.class,()->bookingService.searchBookingByPnr(pnr));
		
		assertEquals("Service.INVALID_PNR",exception.getMessage());
	}
	
//	@Test
//	public void testCancelBookingValid() throws UnitedGoException{
//		String pnr = "URS444";
//		
//		String response =  bookingService.deleteBooking(pnr);
//		
//		assertEquals(response,"booking cancelled successfully without refund");
//	}
	
//	@Test
//	public void testCancelBookingValid2() throws UnitedGoException{
//		String pnr = "URS963";
//		String response = bookingService.deleteBooking(pnr);
//		
//		assertEquals(response,"booking cancelled successfully with a refund initiated");
//	}
	
	

}
