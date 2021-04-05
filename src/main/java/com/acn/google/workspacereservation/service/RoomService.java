package com.acn.google.workspacereservation.service;

import java.util.List;

import com.acn.google.workspacereservation.entity.Rooms;

public interface RoomService {
	
	public List<Rooms> findAll();
	
	public void addRoom();
	
	public Rooms findById(int roomId);

	void deleteById(int theId);

	void saveRoom(Rooms rooms);
}
