package com.lucafontanili.designpatterns.example.java.db.manager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucafontanili.designpatterns.example.java.db.DBConstants;
import com.lucafontanili.designpatterns.example.java.db.dto.Employee;

public class EmployeeManager extends AManager<Employee> {
	private static volatile EmployeeManager instance;
	private static final String TABLENAME = "EMPLOYEE";

	public static EmployeeManager getInstance() {

		EmployeeManager result = instance;
		if (result == null) {
			synchronized (EmployeeManager.class) {
				result = instance;
				if (result == null) {
					result = instance = new EmployeeManager();
				}
			}
		}
		return result;
	}

	public Employee whereUsername(String userName) throws ClassNotFoundException, SQLException, ParseException,
			InstantiationException, IllegalAccessException, InvocationTargetException, JsonProcessingException {

		String query = DBConstants.SELECT + " * " + DBConstants.FROM + " " + TABLENAME + " " + DBConstants.WHERE + " "
				+ Employee.DB_USERNAME + "='" + userName + "\'";
		// List<Employee> out = executeQuery(query, (rs) -> parseResult(rs));
		List<Employee> out = list(query);
		return returnFirst(out);
	}

	public Employee whereEmail(String email) throws SQLException, ClassNotFoundException, ParseException,
			InstantiationException, IllegalAccessException, InvocationTargetException, JsonProcessingException {

		String query = DBConstants.SELECT + " * " + DBConstants.FROM + " " + TABLENAME + " " + DBConstants.WHERE + " "
				+ Employee.DB_EMAIL + "='" + email + "\';";
		// List<Employee> out = executeQuery(query, (rs) -> parseResult(rs));
		List<Employee> out = list(query);
		return returnFirst(out);
	}

	public Employee whereEmailOrUserName(String userName) throws ClassNotFoundException, SQLException, ParseException,
			InstantiationException, IllegalAccessException, InvocationTargetException, JsonProcessingException {

		String query = DBConstants.SELECT + " * " + DBConstants.FROM + " " + TABLENAME + " " + DBConstants.WHERE + " "
				+ Employee.DB_USERNAME + "='" + userName + "\' " + DBConstants.OR + " " + Employee.DB_EMAIL + "='"
				+ userName + "';";
		// List<Employee> out = executeQuery(query, (rs) -> parseResult(rs));
		List<Employee> out = list(query);
		return returnFirst(out);
	}

	@Override
	public Employee read(String id) {
		return read(id, Employee.class);
	}

	@Override
	public void delete(String id) {
		delete(id, Employee.class);
	}

	@Override
	public List list(String query) {
		return list(query, Employee.class);
	}

	protected Employee returnFirst(List<?> out) throws JsonProcessingException {

		if (out != null && !out.isEmpty()) {
			// LOGGER.info(toJson(out.get(0)));
			return (Employee) out.get(0);
		}
		return null;
	}

}
