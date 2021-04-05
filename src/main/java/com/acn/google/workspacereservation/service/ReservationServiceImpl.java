package com.acn.google.workspacereservation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acn.google.workspacereservation.entity.Reservation;
import com.acn.google.workspacereservation.repo.ReservationRepo;

@Service
public class ReservationServiceImpl implements ReservationService {

	private ReservationRepo reservationRepo;
	
	@Autowired
	public ReservationServiceImpl(ReservationRepo theReservationRepo) {
		reservationRepo = theReservationRepo;
	}

	@Override
	public void saveReservation(Reservation reservation) {
		reservationRepo.save(reservation);
		
	}
	
	@Override
	public List<Reservation> findAll() {
		return reservationRepo.findAll();
	}
	
	@Override
	public Reservation findById(int reservationId) {
		Optional<Reservation> result = reservationRepo.findById(reservationId);
		
		Reservation reservation = null;
		
		if (result.isPresent()) {
			reservation = result.get();
		}
		else {
			throw new RuntimeException("Did not find client id - " + reservationId);
		}
		
		return reservation;
	}
	@Override
	public void deleteById(int reservationId) {
		reservationRepo.deleteById(reservationId);
		
	}
	

	

}
