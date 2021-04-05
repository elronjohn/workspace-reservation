package com.acn.google.workspacereservation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acn.google.workspacereservation.entity.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation, Integer> {

}
