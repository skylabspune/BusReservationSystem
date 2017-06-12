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

import com.busreservationsystem.dto.ConductorDto;
import com.busreservationsystem.dto.MapLocationDto;
import com.busreservationsystem.proxy.ConductorProxy;
import com.google.gson.Gson;

@Path("/conductoroperations")
public class ConductorService {

	ConductorProxy conductorProxy = new ConductorProxy();
	Gson gson = new Gson();

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(ConductorDto conductorDto) {
		Map<String, String> result = new HashMap<>();

		result = conductorProxy.login(conductorDto.getUsername(), conductorDto.getPassword());

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@GET
	@Path("/getconductordetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConductorDetails(@QueryParam("username") String username) {
		Map<String, Object> result = new HashMap<>();

		result = conductorProxy.getConductorDetails(username);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@GET
	@Path("/getavailabletickets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailableTickets(@QueryParam("username") String username) {
		Map<String, String> result = new HashMap<>();

		result = conductorProxy.getAvailableTickets(username);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@POST
	@Path("/generatetickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateTickets(ConductorDto conductorDto) {
		Map<String, String> result = new HashMap<>();

		result = conductorProxy.generateTickets(conductorDto);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}

	@POST
	@Path("/updatebuslocation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateConductorLocation(MapLocationDto mapLocationDto, @QueryParam("username") String username) {
		Map<String, String> result = new HashMap<>();

		result = conductorProxy.updateConductorLocation(mapLocationDto, username);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}
	
	@GET
	@Path("/getsoldticketsbycid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSoldTicketsByCId(@QueryParam("cId") String cId) {
		Map<String, String> result = new HashMap<>();

		result = conductorProxy.getSoldTicketsByCId(cId);

		if (result.containsValue("Success")) {
			return Response.ok(200).entity(gson.toJson(result)).build();
		}

		return Response.status(500).entity(gson.toJson(result)).build();
	}
	
}
