package com.busreservationsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.busreservationsystem.connection.DBConnection;
import com.busreservationsystem.entity.Conductor;
import com.busreservationsystem.entity.MapLocation;

public class ConductorDao {

	PassangerDao passangerDao = new PassangerDao();

	public Boolean login(String username, String password) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from conductor where username = '" + username + "' and password = '" + password + "'";
		Boolean result = false;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			con.close();
			result = true;
		}

		con.close();
		return result;
	}

	public Conductor getConductorDetails(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from conductor where username = '" + username + "'";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		Conductor conductor = new Conductor();

		if (rs != null && rs.next()) {
			do {
				conductor.setcId(rs.getInt(1));
				conductor.setBusId(rs.getInt(2));
				conductor.setRouteId(rs.getInt(3));
				conductor.setFirstName(rs.getString(4));
				conductor.setLastName(rs.getString(5));
				conductor.setSource(rs.getString(6));
				conductor.setDestination(rs.getString(7));
				conductor.setWallet(rs.getInt(8));
				conductor.setUsername(rs.getString(9));
				conductor.setPassword(rs.getString(10));

			} while (rs.next());

			return conductor;
		}

		con.close();

		return null;
	}

	public int getAvailableTickets(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select noOftickets from conductor where username = '" + username + "'";
		int remainingTickets = 0;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			remainingTickets = rs.getInt(1);
		}

		con.close();

		return remainingTickets;
	}

	public int generateTickets(int noOfNewTickets, String username) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		String sql = "update conductor set noOfTickets = ? where username = ?";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		int currentNoOfTickets = getAvailableTickets(username);

		ps.setInt(1, noOfNewTickets + currentNoOfTickets);
		ps.setString(2, username);

		result = ps.executeUpdate();

		con.close();

		return result;
	}

	public void addtoConductorWallet(int routeId, int ticketCost) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update conductor set wallet = ? where routeId = ?";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		int currentWalletAmount = getCurrentWalletAmountByRouteID(routeId);

		ps.setInt(1, currentWalletAmount + ticketCost);
		ps.setInt(2, routeId);

		ps.executeUpdate();

		con.close();
	}

	public void updateAvailableTicketCount(int routeId, int availableTickets) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update conductor set noOfTickets = ? where routeId = ?";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);


		ps.setInt(1, availableTickets);
		ps.setInt(2, routeId);

		ps.executeUpdate();

		con.close();
	}
	
	public int getCurrentWalletAmountByRouteID(int routeId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select wallet from conductor where routeId = ?";
		int currentWalletAmount = 0;

		con = DBConnection.getConnection();

		ps = con.prepareStatement(sql);

		ps.setInt(1, routeId);
		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			currentWalletAmount = rs.getInt(1);
		}

		con.close();

		return currentWalletAmount;
	}

	public void updateConductorLocation(MapLocation location, String usrname) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String query = "";

		int cId = getConductorIdByUsername(usrname);

		boolean conductorLocationExists = passangerDao.isExistsInDb("map_location", "cId", cId);

		if (conductorLocationExists) {
			query = "update map_location set latitude = ?, longitude = ? where cId = ?";
		} else {
			query = "insert into map_location(latitude, longitude, cId) values(?,?,?)";
		}

		con = DBConnection.getConnection();
		ps = con.prepareStatement(query);

		ps.setDouble(1, location.getLatitude());
		ps.setDouble(2, location.getLongitude());

		ps.setInt(3, cId);

		result = ps.executeUpdate();
		System.out.println("Result : " + result);

		con.close();
	}

	public int getConductorIdByUsername(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select cId from conductor where username = ?";
		int cId = 0;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setString(1, username);

		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			cId = rs.getInt(1);
		}

		con.close();

		return cId;
	}

	public String getConductorUsernameByRouteId(int routeId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select username from conductor where routeId = ?";
		String username = "";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setInt(1, routeId);

		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			username = rs.getString(1);
		}

		con.close();

		return username;
	}
	
	public int getConductorIdByRouteId(int routeId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select cId from conductor where routeId = ?";
		int cId = 0;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setInt(1, routeId);

		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			cId = rs.getInt(1);
		}

		con.close();

		return cId;
	}

	public int getRouteIdByConductorId(int cId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select routeId  from conductor where cId = ?";
		int routeId = 0;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setInt(1, cId);

		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			routeId = rs.getInt(1);
		}

		con.close();

		return routeId;
	}

	public boolean isConductorLocationExists(int cId) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from map_location where cId = " + cId + ";";
		boolean result = true;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			result = false;
		}

		con.close();

		return result;
	}

	public String getSoldTicketsByCId(String cId) throws NumberFormatException, SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int routeId = getRouteIdByConductorId(Integer.valueOf(cId));

		String sql = "select ticketId from passanger_tickets where routeId = ?";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, routeId);

		rs = ps.executeQuery();

		String ticketIds = "";

		while (rs.next()) {
			if (ticketIds != "") {
				ticketIds = ticketIds.concat(",");
			}
			ticketIds = ticketIds + rs.getInt(1);
		}

		System.out.println(ticketIds);
		return ticketIds;
	}
}