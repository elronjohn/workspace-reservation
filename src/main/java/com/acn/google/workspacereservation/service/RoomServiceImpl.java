package com.acn.google.workspacereservation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acn.google.workspacereservation.entity.Rooms;
import com.acn.google.workspacereservation.repo.RoomsRepo;

@Service
public class RoomServiceImpl implements RoomService {
	
	private RoomsRepo roomsRepo;
	
	@Autowired
	public RoomServiceImpl(RoomsRepo theRoomsRepo) {
		roomsRepo = theRoomsRepo;
	}

	@Override
	public List<Rooms> findAll() {
		
		return roomsRepo.findAll();
	}

	@Override
	public void addRoom() {
		
		
	}
	
	@Override
	public Rooms findById(int roomId) {
		Optional<Rooms> result = roomsRepo.findById(roomId);
		
		Rooms rooms = null;
		
		if (result.isPresent()) {
			rooms = result.get();
		}
		else {
			throw new RuntimeException("Did not find client id - " + roomId);
		}
		
		return rooms;
	}

	@Override
	public void saveRoom(Rooms rooms) {
		roomsRepo.save(rooms);
	}
	
	@Override
	public void deleteById(int roomId) {
		roomsRepo.deleteById(roomId);
	}
}
