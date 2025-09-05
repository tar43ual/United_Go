package com.ual.service;

import com.ual.dto.FlightDTO;
import com.ual.exception.UnitedGoException;

public interface FlightService {
	
	public void addFlight(FlightDTO flightDTO) throws UnitedGoException;
}
