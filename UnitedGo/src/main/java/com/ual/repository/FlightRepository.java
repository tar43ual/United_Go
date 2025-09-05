package com.ual.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ual.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, String> {
	
	@Query("select f from Flight f where f.source = ?1 AND f.destination = ?2 AND f.availableDate = ?3 order by f.fare")
	List<Flight> findFlights(String source, String destination, LocalDate availableDate);
	
	@Modifying
	@Query("update Flight f set f.availableSeats=?1 where f.flightId=?2")
	public void updateSeats(int newAvailableSeats, String flightId);
}
