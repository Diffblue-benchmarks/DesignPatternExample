package com.lucafontanili.designpatterns.example.java.ws;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.lucafontanili.designpatterns.example.java.db.DBConnectionManager;
import com.lucafontanili.designpatterns.example.java.db.DBConstants;
import com.lucafontanili.designpatterns.example.java.db.dto.Session;
import com.lucafontanili.designpatterns.example.java.db.manager.EmployeeManager;
import com.lucafontanili.designpatterns.example.java.db.manager.SessionManager;
import com.lucafontanili.designpatterns.example.java.utility.ws.ErrorConstants;
import com.lucafontanili.designpatterns.example.java.utility.ws.ErrorFR;
import com.lucafontanili.designpatterns.example.java.utility.ws.Header;
import com.lucafontanili.designpatterns.example.java.utility.ws.ServiceResponse;

public abstract class AWS {

	protected static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	protected static final Logger LOGGER = Logger.getLogger(DBConstants.HIBERNATE);
	protected static final SessionManager SESSIONMANAGER = SessionManager.getInstance();
	protected static final EmployeeManager EMPLOYEEMANAGER = EmployeeManager.getInstance();
	protected static DBConnectionManager dbConnectionManager;
	protected static final String MESSAGE = "message";

	protected AWS() {

	}

	static class CheckPattern {
		@FunctionalInterface
		interface CreateResponse {

			ServiceResponse doWork(Session session) throws IOException, NoSuchAlgorithmException,
					InvalidKeySpecException, ClassNotFoundException, SQLException, IllegalArgumentException,
					IllegalAccessException, ParseException, NoSuchFieldException, SecurityException,
					InstantiationException, IllegalAccessException, InvocationTargetException;
		}

		static Response check(HttpServletRequest req, CreateResponse createResponse, String methodName) {

			try {
				Header header = new Header(req);
				Session session = SESSIONMANAGER.read(header.getUserSession().toString());
				// do the authentication cheks
				ServiceResponse out = createResponse.doWork(session);
				if (out.getResponse() == null) {
					out.setResponse(Response.ok().entity(out.getBody().toString()).build());
				}
				LOGGER.info(out.getResponse().getEntity().toString());
				return out.getResponse();
			} catch (Exception e) {
				return returnError(Status.INTERNAL_SERVER_ERROR, ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE,
						ErrorConstants.INTERNAL_SERVER_ERROR_CODE, e, methodName).getResponse();
			}
		}

	}

	protected static ServiceResponse returnError(Status status, String message, int code) {

		ServiceResponse response = new ServiceResponse();
		String error = new ErrorFR(message, code).toString();
		LOGGER.error("Exception: " + error);
		response.setResponse(Response.status(status).entity(error).build());
		return response;
	}

	protected static ServiceResponse returnError(Status status, String message, int code, Exception e,
			String methodName) {

		LOGGER.error("Exception during " + methodName, e);
		return returnError(status, message, code);
	}

}
