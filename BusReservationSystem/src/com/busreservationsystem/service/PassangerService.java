package com.busreservationsystem.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.busreservationsystem.dto.PassangerDto;
import com.busreservationsystem.dto.TicketDto;
import com.busreservationsystem.proxy.PassangerProxy;
import com.google.gson.Gson;

@Path("/passangeroperations")
public class PassangerService {

	Gson gson = new Gson();
	PassangerProxy proxy = new PassangerProxy();

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerPassanger(PassangerDto passangerDto) {
		Map<String, Object> result = new HashMap<>();

		result = proxy.savePassangerDeatils(passangerDto);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(PassangerDto passangerDto) {
		Map<String, String> result = new HashMap<>();

		result = proxy.login(passangerDto.getUsername(), passangerDto.getPassword());

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@POST
	@Path("/addtowallet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addToWallet(PassangerDto passangerDto) {
		Map<String, String> result = new HashMap<>();

		result = proxy.addToWallet(passangerDto.getUsername(), passangerDto.getWallet());

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@GET
	@Path("/getticketdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicketDetails(@QueryParam("username") String username) {
		Map<String, Object> result = new HashMap<>();

		result = proxy.getTicketDetails(username);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@GET
	@Path("/getcurrentwalletamount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentWalletAmount(@QueryParam("username") String username) {
		Map<String, String> result = new HashMap<>();

		result = proxy.getCurrentWalletAmount(username);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@POST
	@Path("/getbuslocation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBusLocation(TicketDto ticketDto) {
		Map<String, Object> result = new HashMap<>();

		result = proxy.getBusLocation(ticketDto);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();

	}

	@POST
	@Path("/generateTicket")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateTicket(TicketDto ticketDto) {
		Map<String, Object> result = new HashMap<>();

		result = proxy.generateTicket(ticketDto);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

}