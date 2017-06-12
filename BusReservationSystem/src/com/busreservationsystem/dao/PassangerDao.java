package com.busreservationsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.busreservationsystem.connection.DBConnection;
import com.busreservationsystem.entity.MapLocation;
import com.busreservationsystem.entity.Passanger;
import com.busreservationsystem.entity.Ticket;

public class PassangerDao {

	public Boolean savePassangerDetails(Passanger passanger) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String query = "insert into passanger(username, password, firstname, lastname, age) values(?,?,?,?,?)";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(query);

		ps.setString(1, passanger.getUsername());
		ps.setString(2, passanger.getPassword());
		ps.setString(3, passanger.getFirstName());
		ps.setString(4, passanger.getLastName());
		ps.setInt(5, passanger.getAge());

		result = ps.executeUpdate();
		System.out.println("Result : " + result);

		con.close();

		if (result > 0) {
			return true;
		}

		return false;
	}

	public Boolean login(String username, String password) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from passanger where username = '" + username + "' and password = '" + password + "'";
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

	public Passanger getPassangerDetails(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from passanger where username = '" + username + "'";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		Passanger passanger = new Passanger();

		if (rs != null && rs.next()) {
			do {
				passanger.setUsername(rs.getString(2));
				passanger.setPassword(rs.getString(3));
				passanger.setFirstName(rs.getString(4));
				passanger.setLastName(rs.getString(5));
				passanger.setAge(rs.getInt(6));
			} while (rs.next());

			return passanger;
		}

		con.close();

		return null;
	}

	public Boolean isUsernameTaken(String username) throws SQLException {
		Passanger passanger = getPassangerDetails(username);

		if (passanger != null) {
			return true;
		}

		return false;
	}

	public int addOrUpdateToWallet(String username, int amount) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "update passanger set wallet = ? where username = ?";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setInt(1, amount);
		ps.setString(2, username);
		result = ps.executeUpdate();

		con.close();

		return result;
	}

	public int getCurrentWalletAmount(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select wallet from passanger where username = ?";
		int walletAmount = 0;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setString(1, username);

		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			walletAmount = rs.getInt(1);
		}

		con.close();

		return walletAmount;
	}

	public Ticket getTicketInfo(String username) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from passanger_tickets where username = ? and isActive = 1";
		Ticket ticket = null;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setString(1, username);

		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			ticket = new Ticket();

			ticket.setTicketId(rs.getInt(1));
			ticket.setSource(rs.getString(2));
			ticket.setDestination(rs.getString(3));
			ticket.setRouteId(rs.getInt(4));
			ticket.setNoOfTickets(rs.getInt(5));
			ticket.setTotalCost(rs.getInt(6));
		}

		con.close();

		return ticket;
	}

	public MapLocation getBusLocation(int cId) throws SQLException {
		MapLocation mapLocation = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from map_location where cId = ?";

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);

		ps.setInt(1, cId);

		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			mapLocation = new MapLocation();

			mapLocation.setLatitude(rs.getDouble(2));
			mapLocation.setLongitude(rs.getDouble(3));
			mapLocation.setcId(rs.getInt(4));
		}

		con.close();

		return mapLocation;
	}

	public void saveTicketDetails(Ticket ticket) throws SQLException {
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int result = 0;
		boolean firstTicket = isExistsInDb("passanger_tickets", "username", ticket.getUsername());
		String query1 = "";
		String query2 = "";

		con = DBConnection.getConnection();

		if (firstTicket) {
			query1 = "update passanger_tickets set isActive = ? where username = ?";
			ps1 = con.prepareStatement(query1);
			ps1.setBoolean(1, false);
			ps1.setString(2, ticket.getUsername());

			ps1.executeUpdate();
		}

		query2 = "insert into passanger_tickets(source, destination, routeId, noOfTickets, totalCost, isActive, username) values(?,?,?,?,?,?,?)";

		ps2 = con.prepareStatement(query2);

		ps2.setString(1, ticket.getSource());
		ps2.setString(2, ticket.getDestination());
		ps2.setInt(3, ticket.getRouteId());
		ps2.setInt(4, ticket.getNoOfTickets());
		ps2.setInt(5, ticket.getTotalCost());
		ps2.setInt(6, ticket.getIsActive());
		ps2.setString(7, ticket.getUsername());

		ps2.executeUpdate();
		System.out.println("Result : " + result);

		con.close();
	}

	public boolean isExistsInDb(String tableName, String columeName, Object columnValue) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";

		System.out.println(columnValue.getClass() + "   786iuwfhkasndfasdnfaksjn");

		sql = "select * from " + tableName + " where " + columeName + " = '" + columnValue + "'";

		boolean result = false;

		con = DBConnection.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		if (rs != null && rs.next()) {
			result = true;
		}

		con.close();

		return result;
	}

}