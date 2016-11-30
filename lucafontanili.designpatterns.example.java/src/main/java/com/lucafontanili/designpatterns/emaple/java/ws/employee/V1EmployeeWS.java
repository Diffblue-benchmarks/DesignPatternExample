package com.lucafontanili.designpatterns.emaple.java.ws.employee;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucafontanili.designpatterns.example.java.db.dto.Employee;
import com.lucafontanili.designpatterns.example.java.db.dto.Session;
import com.lucafontanili.designpatterns.example.java.utility.ws.ErrorConstants;
import com.lucafontanili.designpatterns.example.java.utility.ws.ServiceResponse;
import com.lucafontanili.designpatterns.example.utility.authentication.SSHAUtilities;
import com.lucafontanili.designpatterns.example.utility.authentication.UserSecrets;

public class V1EmployeeWS extends AEmployeeWS {

	V1EmployeeWS() {
	}

	@Override
	public ServiceResponse authentication(String body)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			JsonProcessingException, SQLException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException {

		ServiceResponse response = new ServiceResponse();
		JsonObject data = new JsonParser().parse(body).getAsJsonObject();
		JsonObject output = new JsonObject();
		String userName = new String(Base64.getDecoder().decode(data.get("username").getAsString()),
				StandardCharsets.UTF_8);
		Employee employee = EMPLOYEEMANAGER.whereEmailOrUserName(userName);
		if (employee == null) {
			return returnError(Status.FORBIDDEN, ErrorConstants.EMPLOYEE_AUTHENTICATION_FORBIDDEN_MESSAGE,
					ErrorConstants.EMPLOYEE_AUTHENTICATION_FORBIDDEN_CODE);
		}

		UserSecrets secrets = employee.getSecrets();
		String password = new String(Base64.getDecoder().decode(data.get("password").getAsString()),
				StandardCharsets.UTF_8);
		boolean validPassword = SSHAUtilities.validatePassword(password, secrets);
		if (!validPassword) {
			return returnError(Status.FORBIDDEN, ErrorConstants.EMPLOYEE_AUTHENTICATION_FORBIDDEN_MESSAGE,
					ErrorConstants.EMPLOYEE_AUTHENTICATION_USER_NOT_FOUND_CODE);
		}

		Session session = new Session();
		session.setEmployeeId(employee.getId());
		SESSIONMANAGER.create(session);

		output.addProperty("status", 200);
		output.addProperty("message", "User " + userName + " succesfully logged in");
		output.addProperty("session", session.getId());
		response.setBody(output);
		return response;
	}

	@Override
	public ServiceResponse registration(String body)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			JsonProcessingException, SQLException, ParseException, NoSuchAlgorithmException, InvalidKeySpecException {

		ServiceResponse response = new ServiceResponse();
		JsonObject data = new JsonParser().parse(body).getAsJsonObject();
		JsonObject output = new JsonObject();
		String email = new String(Base64.getDecoder().decode(data.get("email").getAsString()), StandardCharsets.UTF_8);
		Employee employee = EMPLOYEEMANAGER.whereEmail(email);
		if (employee != null) {
			return returnError(Status.FORBIDDEN, ErrorConstants.EMPLOYEE_REGISTRATION_EMAIL_ALREADY_USED_MESSAGE,
					ErrorConstants.EMPLOYEE_REGISTRATION_EMAIL_ALREADY_USED_CODE);
		}
		String username = new String(Base64.getDecoder().decode(data.get("username").getAsString()),
				StandardCharsets.UTF_8);
		employee = EMPLOYEEMANAGER.whereEmailOrUserName(username);
		if (employee != null) {
			return returnError(Status.FORBIDDEN, ErrorConstants.EMPLOYEE_REGISTRATION_USERNAME_ALREADY_USED_MESSAGE,
					ErrorConstants.EMPLOYEE_REGISTRATION_USERNAME_ALREADY_USED_CODE);
		}
		employee = new Employee();
		employee.setEmail(email);
		employee.setUsername(username);
		String password = new String(Base64.getDecoder().decode(data.get("password").getAsString()),
				StandardCharsets.UTF_8);
		employee.setSecrets(SSHAUtilities.hash(password));

		if (data.has(Employee.DB_FIRSTNAME)) {
			employee.setFirstName(data.get(Employee.DB_FIRSTNAME).getAsString());
		}
		if (data.has(Employee.DB_LASTNAME)) {
			employee.setLastName(data.get(Employee.DB_LASTNAME).getAsString());
		}
		if (data.has(Employee.DB_BIRTHDATE)) {
			employee.setBirthDate(formatter.parse(data.get(Employee.DB_BIRTHDATE).getAsString()));
		}
		if (data.has(Employee.DB_FISCALCODE)) {
			employee.setFiscalCode(data.get(Employee.DB_FISCALCODE).getAsString());
		}
		if (data.has(Employee.DB_COMPANYID)) {
			employee.setCompanyId(data.get(Employee.DB_COMPANYID).getAsString());
		}
		EMPLOYEEMANAGER.create(employee);
		// Mail.sendMail(MailConstants.REGISTRATION_SUBJECT,
		// MailConstants.REGISTRATION_CONTENT.replace("<username>",
		// employee.getUsername()).replace("<usercode>",
		// SSHAUtilities.hash(employee.getId(), employee.getSalt()) + "g" +
		// employee.getId()),
		// email);

		output.addProperty("status", 200);
		output.addProperty("message", "User " + email + " succesfully reigstered");
		response.setBody(output);
		return response;
	}

	@Override
	public ServiceResponse actiivation(String id) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, JsonProcessingException, SQLException, ParseException,
			NoSuchFieldException, SecurityException, NoSuchAlgorithmException, InvalidKeySpecException {

		ServiceResponse response = new ServiceResponse();
		JsonObject output = new JsonObject();
		String[] params = id.split("g");
		if (params.length != 2) {
			return returnError(Status.UNAUTHORIZED, ErrorConstants.EMPLOYEE_ACTIVATION_INVALID_HASH_MESSAGE,
					ErrorConstants.EMPLOYEE_ACTIVATION_INVALID_HASH_CODE);
		}

		Employee employee = EMPLOYEEMANAGER.read(params[1]);
		String hash = SSHAUtilities.hash(employee.getId(), employee.getSalt());
		if (!hash.equals(params[0])) {
			return returnError(Status.FORBIDDEN, ErrorConstants.EMPLOYEE_ACTIVATION_INVALID_HASH_MESSAGE,
					ErrorConstants.EMPLOYEE_ACTIVATION_HASH_NOT_MATCHED_CODE);
		}
		employee.setActive(true);
		employee.setActivationDate(new Date());
		// EMPLOYEEMANAGER.update(employee);

		output.addProperty("status", 200);
		output.addProperty("message", "User " + employee.getEmail() + " succesfully activated");
		response.setBody(output);
		return response;
	}

}
