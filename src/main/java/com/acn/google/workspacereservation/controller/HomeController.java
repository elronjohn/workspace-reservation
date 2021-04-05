package com.acn.google.workspacereservation.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acn.google.workspacereservation.entity.Account;
import com.acn.google.workspacereservation.entity.Reservation;
import com.acn.google.workspacereservation.entity.Rooms;
import com.acn.google.workspacereservation.service.AccountService;
import com.acn.google.workspacereservation.service.LoginService;
import com.acn.google.workspacereservation.service.ReservationService;
import com.acn.google.workspacereservation.service.RoomService;

@Controller
@RequestMapping("/index")
public class HomeController {

	Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ReservationService reservationService;


	public int userId;

	public int roomToReserve;


	//--------------------------Register------------------------------

	@GetMapping("")
	public String index(HttpSession httpSession) {
		if(httpSession.getAttribute("username")!=null) {
			return"redirect:/index/reservation";
		}
		return "index";
	}

	@GetMapping("/showFormforReg")
	public String showFormforReg(Model model) {
		//create model to bind form data
		Account account = new Account();
		model.addAttribute("accounts", account);
		return "guest-register";
	}

	@GetMapping("/back")
	public String backToIndex() {
		return "index";
	}

	@PostMapping("/saveAccount")
	public String saveAccount(@ModelAttribute("accounts") Account account) {
		if(!loginService.findByUsername(account.getUsername())) {
			accountService.save(account);
			return"redirect:/index/showFormforLogin";
		}
		return "redirect:/index/showFormforReg?error";
	}
	//------------------------------Login---------------------------------------

	@GetMapping("/showFormforLogin")
	public String loginFormm(Model model) {
		Account account = new Account();
		model.addAttribute("accounts", account);
		return "login";
	}

	@PostMapping("/request")
	public String requestToLog(@ModelAttribute("accounts") Account accounts,HttpSession httpSession) {
		if(!loginService.findByUsername(accounts.getUsername())) {
			return "redirect:/index/showFormforLogin?notFound";
		}
		log.info(loginService.getByUsername(accounts.getUsername()).getPassword());
		if(loginService.findByUsername(accounts.getUsername())) {
			Account acc = new Account();
			acc=loginService.getByUsername(accounts.getUsername());
			if(acc.getUsername().equals(accounts.getUsername()) 
					&& acc.getPassword().equals(accounts.getPassword())) {
				httpSession.setAttribute("username", acc.getUsername());
				httpSession.setAttribute("name", acc.getFirstName()+" "+acc.getLastName());
				httpSession.setAttribute("accType", acc.getAccount_type());
				httpSession.setAttribute("accId", acc.getAccount_id());
				userId = acc.getAccount_id();				
				return"redirect:/index/reservation";
			}
		}
		return "redirect:/index/showFormforLogin?error";
	}

	//------------------------------------Reservation-----------------------------
	@GetMapping("/reservation")
	public String reservationView(Model model, HttpSession httpSession) {
		if(httpSession.getAttribute("username") == null) {
			return"redirect:/index";
		}
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("\"EEE, MMM dd, ''yyyy\"");
		model.addAttribute("dateNow", today.format(dateFormat));
		List<Rooms> theRooms = roomService.findAll();
		model.addAttribute("rooms", theRooms);
		model.addAttribute("roomSize", theRooms.size());
		return "reservation";
	}

	@GetMapping("/reserveRoom")
	public String reserveRoom(@RequestParam("roomId") int roomId, Model model) {
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("\"EEE, MMM dd, ''yyyy\"");
		model.addAttribute("dateNow", today.format(dateFormat));
		Rooms rooms = roomService.findById(roomId);
		model.addAttribute("rooms", rooms);
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		roomToReserve = roomId;
		return "reserve-room";
	}
	
	@PostMapping("/saveReservation")
	public String saveReservation(@ModelAttribute("reservation") Reservation reservation,HttpSession httpSession) {
		Account account = accountService.findById(Integer.parseInt(httpSession.getAttribute("accId").toString()));
		Rooms rooms = roomService.findById(roomToReserve);
		reservation.setAccountId(account);
		reservation.setRoomId(rooms);
		rooms.setStatus("Reserved");
		roomService.saveRoom(rooms);
		reservationService.saveReservation(reservation);
		return"redirect:/index/reserved-rooms?keyword";
	}



	//------------------------------------Reserved Rooms----------------------------
	@GetMapping("/reserved-rooms")
	public String reservedRoomsView(Model model, @RequestParam("keyword") String keyword,
									HttpSession httpSession) {
		if(httpSession.getAttribute("username") == null) {
			return"redirect:/index";
		}
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("\"EEE, MMM dd, ''yyyy\"");
		model.addAttribute("dateNow", today.format(dateFormat));
		List<Reservation> reserve2 = new ArrayList<Reservation>();
		List<Reservation> reservations = reservationService.findAll();
		for(Reservation reserve : reservations ) {
			if(reserve.getStartDate().toString().contains(keyword)
					||reserve.getEndDate().toString().contains(keyword)) {
				reserve2.add(reserve);
			}
		}
        model.addAttribute("reservations", reserve2);
        model.addAttribute("keyword", keyword);
		return "reserved-rooms";
	}
	
	@GetMapping("/updateReservation")
	public String updateReservation(@RequestParam("reservationId") int reservationId, Model model) {
		Reservation reservation = reservationService.findById(reservationId);
		model.addAttribute("reservation", reservation);
		return "update-reservation";

	}
	
	@PostMapping("/toUpdateReservation")
	public String toUpdateReservation(@ModelAttribute("reservation") Reservation reservation) {
		reservationService.saveReservation(reservation);
		return"redirect:/index/reserved-rooms?keyword";
	}

	
	@GetMapping("/deleteReservation")
	public String deleteReservation(@RequestParam("reservationId") int reservationId) {
		log.info("===========" + reservationId);
		Reservation reservation = reservationService.findById(reservationId);
		Rooms room = reservation.getRoomId();
		room.setStatus("Available");
		roomService.saveRoom(room);
		reservationService.deleteById(reservationId);
		return "redirect:/index/reserved-rooms?keyword";
	}


	//--------------------------------------------Rooms----------------------------
	@GetMapping("/rooms")
	public String roomsView(Model model, HttpSession httpSession) {
		if(httpSession.getAttribute("username") == null) {
		return"redirect:/index";
		}
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("\"EEE, MMM dd, ''yyyy\"");
		model.addAttribute("dateNow", today.format(dateFormat));
		List<Rooms> theRooms = roomService.findAll();	
		model.addAttribute("rooms", theRooms);
		return "list-rooms";
	}

	@GetMapping("/addRoom")
	public String addRoomView(Model model, HttpSession httpSession) {
		if(httpSession.getAttribute("username") == null) {
		return"redirect:/index";
		}
		
		List<String> roomType = Arrays.asList("Office", "Meeting", "Seminar");
		model.addAttribute("roomType", roomType);
		Rooms rooms = new Rooms();
		model.addAttribute("room", rooms);
		return "add-room";
	}

	@GetMapping("/updateRoom")
	public String updateRoom(@RequestParam("roomId") int roomId, Model model,
					HttpSession httpSession) {
		if(httpSession.getAttribute("username") == null) {
		return"redirect:/index";
		}
		Rooms rooms = roomService.findById(roomId);
		List<String> roomType = Arrays.asList("Office", "Meeting", "Seminar");
		model.addAttribute("roomType", roomType);
		model.addAttribute("room", rooms);
		return "update-room";

	}

	@PostMapping("/saveRoom")
	public String saveRoom(@ModelAttribute("room") Rooms rooms) {
		roomService.saveRoom(rooms);
		return"redirect:/index/rooms";
	}

	@GetMapping("/deleteRoom")
	public String deleteRoom(@RequestParam("roomId") int roomId) {
		
		roomService.deleteById(roomId);

		return "redirect:/index/rooms";

	}

	//-----------------------------------Accounts-----------------------------------------
	@GetMapping("/accounts")
	public String accountsView(Model model,
					HttpSession httpSession) {
		if(httpSession.getAttribute("username") == null) {
		return"redirect:/index";
		}
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("\"EEE, MMM dd, ''yyyy\"");
		model.addAttribute("dateNow", today.format(dateFormat));
		List<Account> theAccounts = accountService.findAll();
		model.addAttribute("accounts", theAccounts);

		return "list-accounts";
	}

	@GetMapping("/updateAccount")
	public String updateAccount(@RequestParam("accountId") int accountId,
			Model model) {
		Account account = accountService.findById(accountId);
		List<String> accountType = Arrays.asList("guest", "staff", "admin");
		model.addAttribute("accountType", accountType);
		model.addAttribute("account", account);
		return "update-accounts";
	}

	@GetMapping("/updateGuestOrStaffAccount")
	public String updateGuestOrStaffAccount(@RequestParam("accountId") int accountId,
			Model model) {
		Account account = accountService.findById(accountId);
		model.addAttribute("account", account);
		return "update-useraccount";
	}

	@GetMapping("/addPersonnelAccount")
	public String addPersonnelAccountView(Model model) {
		Account account = new Account();
		model.addAttribute("accounts", account);
		List<String> accountType = Arrays.asList("guest", "staff", "admin");
		model.addAttribute("accountType", accountType);
		return "personnel-accountreg";
	}

	@PostMapping("/savePersonnelAccount")
	public String savePersonnelAccount(@ModelAttribute("clients") Account account) {
		accountService.savePersonnel(account);
		return"redirect:/index/accounts";
	}

	@PostMapping("/saveEditedAccount")
	public String saveEditedAccount(@ModelAttribute("account") Account account) {

		accountService.savePersonnel(account);

		return "redirect:/index/reservation";
	}

	@GetMapping("/deleteAccount")
	public String deleteAccount(@RequestParam("accountId") int accountId) {

		accountService.deleteById(accountId);

		return "redirect:/index/accounts";

	}
	//---------------------------------Logout--------------------------------------
	@GetMapping("/logout")
	public String logout(HttpSession httpSession) {
		userId = 0;
		httpSession.setAttribute("username", null);
		httpSession.setAttribute("name", null);
		return "redirect:/index";
	}




}
