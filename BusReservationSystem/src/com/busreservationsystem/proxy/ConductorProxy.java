package com.busreservationsystem.proxy;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.busreservationsystem.dao.ConductorDao;
import com.busreservationsystem.dao.PassangerDao;
import com.busreservationsystem.dto.ConductorDto;
import com.busreservationsystem.dto.MapLocationDto;
import com.busreservationsystem.entity.Conductor;
import com.busreservationsystem.entity.MapLocation;
import com.google.gson.Gson;

public class ConductorProxy {

	ConductorDao conductorDao = new ConductorDao();
	PassangerDao passangerDao = new PassangerDao();

	Gson gson = new Gson();

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
			Boolean authenticate = conductorDao.login(username, password);

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

	public Map<String, Object> getConductorDetails(String username) {
		Map<String, Object> resultMap = new HashMap<>();

		Conductor conductor = null;

		if (username == null || "".equals(username.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		try {
			conductor = conductorDao.getConductorDetails(username);

			if (conductor == null) {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "No conductor exists with entered username!");
			} else {
				resultMap.put("Result", "Success");
				resultMap.put("source", conductor.getSource());
				resultMap.put("destination", conductor.getDestination());
				resultMap.put("routeId", conductor.getRouteId());
				resultMap.put("busId", conductor.getBusId());
				resultMap.put("wallet", conductor.getWallet());
			}

		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, String> getAvailableTickets(String username) {
		Map<String, String> resultMap = new HashMap<>();

		int availableTickets = 0;

		if (username == null || "".equals(username.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		try {
			availableTickets = conductorDao.getAvailableTickets(username);

			if (availableTickets == 0) {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "All tickets sold out!!!");
			} else {
				resultMap.put("Result", "Success");
				resultMap.put("Message", "Tickets still available!");
				resultMap.put("availableTickets", Integer.toString(availableTickets));
			}

		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, String> generateTickets(ConductorDto conductorDto) {
		Map<String, String> resultMap = new HashMap<>();

		if (conductorDto.getUsername() == null || "".equals(conductorDto.getUsername().trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		if (conductorDto.getNoOfTickets() == 0) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid no. of tickets to be generated!!!");
			return resultMap;
		}

		try {
			int result = conductorDao.generateTickets(conductorDto.getNoOfTickets(), conductorDto.getUsername());

			if (result > 0) {
				resultMap.put("Result", "Success");
				resultMap.put("Message", "Tickets generated successfully");
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "No such conductor");
			}

		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}

	public Map<String, String> updateConductorLocation(MapLocationDto mapLocationDto, String username) {
		Map<String, String> resultMap = new HashMap<>();

		if (username == null || "".equals(username.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid username");
			return resultMap;
		}

		MapLocation mapLocation = new MapLocation();

		mapLocation.setLatitude(mapLocationDto.getLatitude());
		mapLocation.setLongitude(mapLocationDto.getLongitude());

		try {

			boolean isConductorExists = passangerDao.isExistsInDb("conductor", "username", username);
			if (isConductorExists) {
				conductorDao.updateConductorLocation(mapLocation, username);

				resultMap.put("Result", "Success");
				resultMap.put("Message", "Map location updated successfully");
			} else {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "Invalid Conductor");
			}
		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}
	
	public Map<String, String> getSoldTicketsByCId(String cId) {
		Map<String, String> resultMap = new HashMap<>();

		if (cId == null || "".equals(cId.trim())) {
			resultMap.put("Result", "Data insufficient");
			resultMap.put("Message", "Please enter valid cnductr ID");
			return resultMap;
		}

		try {
			String ticketIds = conductorDao.getSoldTicketsByCId(cId);

			if (ticketIds == null || ticketIds.equals("")) {
				resultMap.put("Result", "Error");
				resultMap.put("Message", "No ticket sold out!!!");
			} else {
				resultMap.put("Result", "Success");
				resultMap.put("soldTickets", ticketIds);
			}

		} catch (SQLException e) {
			resultMap.put("Result", "Error");
			resultMap.put("Message", "DB Connection error");
			resultMap.put("Exception", e.getMessage());
		}

		return resultMap;
	}
}
