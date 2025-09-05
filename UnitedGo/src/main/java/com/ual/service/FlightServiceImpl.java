package com.ual.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ual.dto.FlightDTO;
import com.ual.entity.Flight;
import com.ual.exception.UnitedGoException;
import com.ual.repository.FlightRepository;

@Transactional
@Service(value="flightService")
public class FlightServiceImpl implements FlightService{
	
	@Autowired
	private FlightRepository flightRepository;
	
	private ModelMapper mapper = new ModelMapper();

	@Override
	public void addFlight(FlightDTO flightDTO) throws UnitedGoException {
		
		Flight flight = mapper.map(flightDTO, Flight.class);
		
		flightRepository.saveAndFlush(flight);
		
	}

}
