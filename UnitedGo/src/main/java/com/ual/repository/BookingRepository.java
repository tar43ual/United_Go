package com.ual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ual.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {

}
