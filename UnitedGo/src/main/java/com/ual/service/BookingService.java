package com.ual.service;

import java.time.LocalDate;
import java.util.List;

import com.ual.dto.BookingDTO;
import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;

public interface BookingService {
	
	public List<FlightDTO> searchFlights(String source,String destination,LocalDate date) throws UnitedGoException;
	
	public String addBooking(Integer noOfPassengers,String flightId) throws UnitedGoException;
	
	public BookingDTO searchBookingByPnr(String pnr) throws UnitedGoException;
	
	public String deleteBooking(String pnr) throws UnitedGoException;
}
