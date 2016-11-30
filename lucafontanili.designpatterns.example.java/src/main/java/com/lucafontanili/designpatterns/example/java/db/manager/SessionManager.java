package com.lucafontanili.designpatterns.example.java.db.manager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lucafontanili.designpatterns.example.java.db.DBConstants;
import com.lucafontanili.designpatterns.example.java.db.dto.Session;

public class SessionManager extends AManager<Session> {
	private static volatile SessionManager instance;
	private static final String TABLENAME = "Session";

	public static SessionManager getInstance() {

		SessionManager result = instance;
		if (result == null) {
			synchronized (SessionManager.class) {
				result = instance;
				if (result == null) {
					result = instance = new SessionManager();
				}
			}
		}
		return result;
	}

	public List<Session> list() throws ClassNotFoundException, SQLException, ParseException, InstantiationException,
			IllegalAccessException, InvocationTargetException, JsonProcessingException {

		String query = DBConstants.SELECT + " * " + DBConstants.FROM + " " + TABLENAME;
		return list(query);
	}

	@Override
	public Session read(String id) {
		return read(id, Session.class);
	}

	@Override
	public void delete(String id) {
		delete(id, Session.class);

	}

	@Override
	public List<Session> list(String query) {
		return list(query, Session.class);
	}

}
