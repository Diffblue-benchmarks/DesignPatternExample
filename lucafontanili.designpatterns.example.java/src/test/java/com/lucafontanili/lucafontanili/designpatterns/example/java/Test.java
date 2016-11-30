package com.lucafontanili.lucafontanili.designpatterns.example.java;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucafontanili.designpatterns.emaple.java.ws.employee.AEmployeeWS;
import com.lucafontanili.designpatterns.example.utility.authentication.SSHAUtilities;

public class Test {

	public static void main(String[] args)
			throws ClassNotFoundException, JsonProcessingException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SQLException, InstantiationException, ParseException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchFieldException, SecurityException {

		// System.setProperty(DBConstants.CONFIGURATION_PATH, ".");
		// Employee e = new Employee();
		// e.setFirstName("Luca");
		// e.setLastName("Fontanili");
		// e.setFiscalCode("FNTLCU90A22G337I");
		// e.setSupervisor(false);
		// e.setActive(true);
		// e.setEmail("lvito90@gmail.com");
		// EmployeeManager em = EmployeeManager.getInstance();
		// em.create(e);
		// e.setId(UUID.randomUUID().toString());
		// em.insert(e);
		// System.out.println(em.whereId(e.getId()).toString());
		// String body = "{ \"password\":\"MTIzNDU2Nzg=\",
		// \"username\":\"bHZpdG85MA==\",
		// \"email\":\"bHVjYS5mb250YW5pbGk5M0BnbWFpbC5jb20=\"}";
		//
		// System.out.println(AEmployeeWS.registrationResponse(body, "v1"));

		// String body =
		// "{\"password\":\"MTIzNDU2Nzg=\",\"username\":\"bHVjYS5mb250YW5pbGk5M0BnbWFpbC5jb20=\"}";
		// System.out.println(AEmployeeWS.authenticationResponse(body, "v1"));

		String id = SSHAUtilities.hash("bd683447-371e-4a21-aa58-639277e0a996", "2c1ce30f6dcf01c42a5c84b867757c05");
		System.out.print(AEmployeeWS.activationResponse(id + "gbd683447-371e-4a21-aa58-639277e0a996", "v1"));
	}

}
