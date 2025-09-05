package com.ual.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ual.dto.BookingDTO;
import com.ual.dto.FlightDTO;
import com.ual.entity.Booking;
import com.ual.entity.Flight;
import com.ual.exception.UnitedGoException;
import com.ual.repository.BookingRepository;
import com.ual.repository.FlightRepository;

@Service(value = "bookingService")
@Transactional
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private FlightRepository flightRepository;

	private ModelMapper mapper = new ModelMapper();

	@Override
	public List<FlightDTO> searchFlights(String source, String destination, LocalDate date) throws UnitedGoException {
		
		if(source.isEmpty()) {
			throw new UnitedGoException("Service.NO_SOURCE_FOUND");
		}else if(destination.isEmpty()) {
			throw new UnitedGoException("Service.NO_DESTINATION_FOUND");
		}else if(date == null) {
			throw new UnitedGoException("Service.NO_DATE_FOUND");
		}

		List<Flight> list = flightRepository.findFlights(source, destination, date);

		if (list.isEmpty()) {
			throw new UnitedGoException("Service.NO_FLIGHTS_FOUND");
		}

		return list.stream().map(p -> mapper.map(p, FlightDTO.class)).collect(Collectors.toList());

	}

	@Override
	public String addBooking(Integer noOfPassengers, String flightId) throws UnitedGoException {

		int randomThreeDigit = ThreadLocalRandom.current().nextInt(100, 1000);
		String PNR = "URS" + randomThreeDigit;

		Optional<Flight> optional = flightRepository.findById(flightId);

		Flight flight = optional.orElseThrow(()->new UnitedGoException("Service.INVALID_FLIGHT_ID"));

		if (noOfPassengers > flight.getAvailableSeats()) {
			throw new UnitedGoException("Service.LESS_SEAT");
		}

		FlightDTO ft = mapper.map(flight, FlightDTO.class);
		double flightFare = optional.get().getFare();

		double totalFare = (double) flightFare * noOfPassengers;

		BookingDTO booking = new BookingDTO(PNR, noOfPassengers, totalFare, ft);

		Booking bk = mapper.map(booking, Booking.class);
		bookingRepository.saveAndFlush(bk);

		int updatedSeats = flight.getAvailableSeats() - noOfPassengers;

//		flightRepository.updateSeats(updatedSeats, flightId);
		flight.setAvailableSeats(updatedSeats);
		flightRepository.save(flight);

		return PNR;
	}

	@Override
	public BookingDTO searchBookingByPnr(String pnr) throws UnitedGoException {

		Optional<Booking> optional = bookingRepository.findById(pnr);
		Booking booking = optional.orElseThrow(() -> new UnitedGoException("Service.INVALID_PNR"));
		booking = optional.get();

		BookingDTO bookingDTO = mapper.map(booking, BookingDTO.class);

		return bookingDTO;
	}

	@Override
	public String deleteBooking(String pnr) throws UnitedGoException {


		Optional<Booking> optional = bookingRepository.findById(pnr);
		Booking booking = optional.orElseThrow(() -> new UnitedGoException("Service.INVALID_PNR"));
		Flight flight = booking.getFlight();
		LocalDate flightDate = flight.getAvailableDate();
		String depTime = flight.getDepartureTime();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse(depTime, timeFormatter);
		LocalDateTime dateOfJourney = LocalDateTime.of(flightDate, time);
		LocalDateTime cancellationCutOff = dateOfJourney.minusHours(6);
		LocalDateTime now = LocalDateTime.now();

		if (now.isEqual(dateOfJourney)) {
			throw new UnitedGoException("Service.CANCELLATION_NOT_ALLOWED");
		}
		
		if(now.isAfter(dateOfJourney)) {
			throw new UnitedGoException("Service.CANCELLATION_NOT_ALLOWED_FOR_PAST");
		}

		Integer noOfAvailableSeats = flight.getAvailableSeats();
		Integer cancelledSeats = booking.getNoOfPassengers();
		flight.setAvailableSeats(noOfAvailableSeats + cancelledSeats);
		flightRepository.saveAndFlush(flight);
		booking.setFlight(null);
		bookingRepository.delete(booking);

		if (now.isBefore(cancellationCutOff)) {

			return "booking cancelled successfully with a refund initiated";

		}

		return "booking cancelled successfully without refund";

	}

}
