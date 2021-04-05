package com.acn.google.workspacereservation.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="rooms")
public class Rooms {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="room_id")
	private int room_id;
	
	@Column(name="room_number")
	private String room_number;
	
	@Column(name="room_type")
	private String room_type;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="status")
	private String status="Available";
	
	@OneToMany(mappedBy="roomId",
			cascade={CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH})
	private List<Reservation> reservation;
	
	public Rooms() {
	
	}

	public Rooms(int room_id, String room_number, String room_type, Double price) {
		super();
		this.room_id = room_id;
		this.room_number = room_number;
		this.room_type = room_type;
		this.price = price;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public String getRoom_number() {
		return room_number;
	}

	public void setRoom_number(String room_number) {
		this.room_number = room_number;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}
	
	
	
}
