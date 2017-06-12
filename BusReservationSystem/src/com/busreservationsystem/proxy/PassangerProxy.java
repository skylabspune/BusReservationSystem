package com.busreservationsystem.proxy;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.busreservationsystem.dao.ConductorDao;
import com.busreservationsystem.dao.PassangerDao;
import com.busreservationsystem.dto.PassangerDto;
import com.busreservationsystem.dto.TicketDto;
import com.busreservationsystem.entity.MapLocation;
import com.busreservationsystem.entity.Passanger;
import com.busreservationsystem.entity.Ticket;
import com.google.gson.Gson;

public class PassangerProxy {

	PassangerDao passangerDao = new PassangerDao();
	ConductorDao conductorDao = new ConductorDao();
	Gson gson = new Gson();

	public Map<String, Object> savePassangerDeatils(PassangerDto passangerDto) {
		Map<String, Object> resultMap = new HashMap<>();
		Passanger passanger = new Passanger();

		if (passangerDto.getUsername() != null && !"".equals(passangerDto.getUsername().trim())) {
			passanger.setUsername(passangerDto.getUsername());
		} else {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		if (passangerDto.getPassword() != null && !"".equals(passangerDto.getPassword().trim())) {
			passanger.setPassword(passangerDto.getPassword());
		} else {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid password");
			return resultMap;
		}

		passanger.setFirstName(passangerDto.getFirstName());
		passanger.setLastName(passangerDto.getLastName());
		passanger.setAge(passangerDto.getAge() != null && !"".equals(passangerDto.getAge()) ? Integer.parseInt(passangerDto.getAge()) : 0);

		try {

			Boolean usernameTaken = passangerDao.isUsernameTaken(passangerDto.getUsername());

			if (usernameTaken) {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Username already taken. Please try new one.");
				return resultMap;
			}

			Boolean result = passangerDao.savePassangerDetails(passanger);

			if (result) {
				resultMap.put("Result", "Success");
				resultMap.put("Message", "Registration Successful!");
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Registration Failed!");
			}
		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, String> login(String username, String password) {
		Map<String, String> resultMap = new HashMap<>();

		if (username == null || "".equals(username.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		if (password == null || "".equals(password.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		try {
			Boolean authenticate = passangerDao.login(username, password);

			if (authenticate) {
				resultMap.put("Result", "Success");
				resultMap.put("Message", "Login successful");
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Authentication failed!");
			}
		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, String> addToWallet(String username, int amount) {
		Map<String, String> resultMap = new HashMap<>();

		if (username == null || "".equals(username.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		if (amount == 0) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid amount to add to wallet");
			return resultMap;
		}

		try {
			int currentWalletAmount = passangerDao.getCurrentWalletAmount(username);

			int result = passangerDao.addOrUpdateToWallet(username, amount + currentWalletAmount);

			if (result > 0) {
				resultMap.put("Result", "Success");
				resultMap.put("Message", "Amount added to wallet successfully");
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Invalid User");
			}

		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, String> getCurrentWalletAmount(String username) {
		Map<String, String> resultMap = new HashMap<>();

		if (username == null || "".equals(username.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		try {
			int currentWalletAmount = passangerDao.getCurrentWalletAmount(username);

			if (currentWalletAmount > 0) {
				resultMap.put("Result", "Success");
				resultMap.put("currentWalletAmount", String.valueOf(currentWalletAmount));
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Your wallet is empty!!!");
			}

		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, Object> getTicketDetails(String username) {
		Map<String, Object> resultMap = new HashMap<>();

		if (username == null || "".equals(username.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		try {
			Ticket ticket = passangerDao.getTicketInfo(username);

			if (ticket != null) {
				TicketDto ticketDto = new TicketDto();

				ticketDto.setTicketId(ticket.getTicketId());
				ticketDto.setSource(ticket.getSource());
				ticketDto.setDestination(ticket.getDestination());
				ticketDto.setNoOfTickets(ticket.getNoOfTickets());
				ticketDto.setRouteId(ticket.getRouteId());
				ticketDto.setTotalCost(ticket.getTotalCost());

				resultMap.put("Result", "Success");
				resultMap.put("ticketId", ticket.getTicketId());
				resultMap.put("source", ticket.getSource());
				resultMap.put("destination", ticket.getDestination());
				resultMap.put("noOfTickets", ticket.getNoOfTickets());
				resultMap.put("routeId", ticket.getRouteId());
				resultMap.put("totalCost", ticket.getTotalCost());
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "No ticket available");
			}

		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, Object> getBusLocation(TicketDto ticketDto) {
		Map<String, Object> resultMap = new HashMap<>();
		try {

			int cId = conductorDao.getConductorIdByRouteId(ticketDto.getRouteId());

			MapLocation mapLocation = passangerDao.getBusLocation(cId);

			if (mapLocation == null) {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "No bus available for the specified route");
				return resultMap;
			}

			boolean userExists = passangerDao.isUsernameTaken(ticketDto.getUsername());

			if (userExists) {
				resultMap.put("Result", "Success");
				resultMap.put("latitude", mapLocation.getLatitude());
				resultMap.put("longitude", mapLocation.getLongitude());
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Invalid User");
			}
		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, Object> generateTicket(TicketDto ticketDto) {
		Map<String, Object> resultMap = new HashMap<>();
		boolean userExists;
		int availableTickets = 0;
		try {
			userExists = passangerDao.isUsernameTaken(ticketDto.getUsername());
			if (userExists) {
				Ticket ticket = new Ticket();

				ticket.setSource(ticketDto.getSource());
				ticket.setDestination(ticketDto.getDestination());
				ticket.setRouteId(ticketDto.getRouteId());
				ticket.setNoOfTickets(ticketDto.getNoOfTickets());
				ticket.setTotalCost(ticketDto.getTotalCost());
				ticket.setUsername(ticketDto.getUsername());
				ticket.setIsActive(1);

				int wallet = passangerDao.getCurrentWalletAmount(ticketDto.getUsername());

				int remainingWalletAmount = wallet - ticket.getTotalCost();

				if (remainingWalletAmount < 0) {
					resultMap.put("Result", "Error");
					resultMap.put("Message", "Your wallet has only " + wallet + " Rs. Please add money to your wallet.");
				} else {
					String condUsername = conductorDao.getConductorUsernameByRouteId(ticketDto.getRouteId());
					availableTickets = conductorDao.getAvailableTickets(condUsername);
					
					int remainingTickets = availableTickets - ticketDto.getNoOfTickets();
					
					if (remainingTickets < 0) {
						resultMap.put("Result", "Error");
						resultMap.put("Message", "Sorry, only " + availableTickets + " tickets available.");
					} else {
						passangerDao.addOrUpdateToWallet(ticketDto.getUsername(), remainingWalletAmount);

						conductorDao.addtoConductorWallet(ticket.getRouteId(), ticket.getTotalCost());
						conductorDao.updateAvailableTicketCount(ticket.getRouteId(), remainingTickets);
						passangerDao.saveTicketDetails(ticket);

						resultMap.put("Result", "Success");
						resultMap.put("Message", "Ticket generated successfully");
					}
				}
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Invalid User");
			}
		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}
		return resultMap;
	}
}