package com.lucafontanili.designpatterns.emaple.java.ws.employee;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucafontanili.designpatterns.example.java.utility.ws.ServiceResponse;
import com.lucafontanili.designpatterns.example.java.ws.AWS;

public abstract class AEmployeeWS extends AWS {

	protected AEmployeeWS() {

	}

	private static AEmployeeWS getEmployeeWS(String wsVersion) throws ClassNotFoundException {

		switch (wsVersion) {
		case "v1":
			return new V1EmployeeWS();
		default:
			throw new ClassNotFoundException();
		}
	}

	public static ServiceResponse authenticationResponse(String body, String wsVersion)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			JsonProcessingException, SQLException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException {

		return getEmployeeWS(wsVersion).authentication(body);
	}

	public static ServiceResponse registrationResponse(String body, String wsVersion)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			JsonProcessingException, SQLException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException {

		return getEmployeeWS(wsVersion).registration(body);
	}

	public static ServiceResponse activationResponse(String id, String wsVersion)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			JsonProcessingException, SQLException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchFieldException, SecurityException {

		return getEmployeeWS(wsVersion).actiivation(id);
	}

	protected abstract ServiceResponse authentication(String body)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			JsonProcessingException, SQLException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException;

	protected abstract ServiceResponse registration(String body)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			JsonProcessingException, SQLException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException;

	protected abstract ServiceResponse actiivation(String id) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, JsonProcessingException, SQLException, ParseException,
			NoSuchFieldException, SecurityException, NoSuchAlgorithmException, InvalidKeySpecException;

}
