package com.lucafontanili.designpatterns.example.java.ws;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lucafontanili.designpatterns.emaple.java.ws.employee.AEmployeeWS;

@Path("/employee")
public class EmployeeWS extends AWS {

	@Context
	private HttpServletRequest req;

	@POST
	@Path("/authentication")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authentication(@FormParam("body") String body) {

		return AWS.CheckPattern.check(req,
				(session) -> AEmployeeWS.authenticationResponse(body, req.getHeader("wsVersion")),
				"employee/autentication");
	}

	@POST
	@Path("/registration")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(@FormParam("body") String body) {

		return AWS.CheckPattern.check(req,
				(session) -> AEmployeeWS.registrationResponse(body, req.getHeader("wsVersion")),
				"employee/registration");
	}

	@GET
	@Path("/activation")
	@Produces(MediaType.TEXT_HTML)
	public Response activation(@QueryParam("id") String id) {

		return AWS.CheckPattern.check(req, (session) -> AEmployeeWS.activationResponse(id, req.getHeader("wsVersion")),
				"employee/activation");
	}

}
