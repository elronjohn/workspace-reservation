package com.acn.google.workspacereservation.service;

import java.util.List;

import com.acn.google.workspacereservation.entity.Reservation;

public interface ReservationService {

	void saveReservation(Reservation reservation);

	List<Reservation> findAll();

	void deleteById(int reservationId);

	Reservation findById(int reservationId);
	
	
}
