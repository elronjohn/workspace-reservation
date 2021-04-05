package com.acn.google.workspacereservation.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="reserved_rooms")
public class Reservation {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="room_details")
	private String roomDetails;
	
	@Column(name="start_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@Column(name="end_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	@Column(name="reserved_to")
	private String reservedTo;
	
	@Column(name="reserved_by")
	private String reservedBy;
	
	@Column(name="amount")
	private String amount;
	
	@ManyToOne(cascade={CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="account_id")
	public Account accountId;
	
	@ManyToOne(cascade={CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH})
	@JoinColumn(name="room_id")
	public Rooms roomId;

	public Reservation() {
		
	}

	public Reservation(int id, String roomDetails, LocalDate startDate, LocalDate endDate, String reservedTo,
			String reservedBy, String amount, Account accountId, Rooms roomId) {
		super();
		this.id = id;
		this.roomDetails = roomDetails;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservedTo = reservedTo;
		this.reservedBy = reservedBy;
		this.amount = amount;
		this.accountId = accountId;
		this.roomId = roomId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomDetails() {
		return roomDetails;
	}

	public void setRoomDetails(String roomDetails) {
		this.roomDetails = roomDetails;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReservedTo() {
		return reservedTo;
	}

	public void setReservedTo(String reservedTo) {
		this.reservedTo = reservedTo;
	}

	public String getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(String reservedBy) {
		this.reservedBy = reservedBy;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Account getAccountId() {
		return accountId;
	}

	public void setAccountId(Account accountId) {
		this.accountId = accountId;
	}

	public Rooms getRoomId() {
		return roomId;
	}

	public void setRoomId(Rooms roomId) {
		this.roomId = roomId;
	}

	
	
	
	
}
