package com.lucafontanili.designpatterns.example.java.db.manager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lucafontanili.designpatterns.example.java.db.DBConnectionManager;
import com.lucafontanili.designpatterns.example.java.db.DBConstants;
import com.lucafontanili.designpatterns.example.java.db.dto.ADTO;

public abstract class AManager<E extends ADTO> {

	protected static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	protected static final Logger LOGGER = Logger.getLogger(DBConstants.HIBERNATE);
	protected Connection connection;
	protected Statement statement;
	protected ResultSet resultSet;

	protected static final DBConnectionManager DB_CONNECTION_MANAGER = DBConnectionManager.getInstance();

	@Deprecated
	void openConnection() throws SQLException {

		connection = DBConnectionManager.connect();
		statement = connection.createStatement();
	}

	@Deprecated
	void closeConnection(boolean closeResult) throws SQLException {

		if (closeResult) {
			resultSet.close();
		}
		statement.close();
		connection.close();
	}

	@FunctionalInterface
	interface Query {

		List doWork(ResultSet resultSet) throws ClassNotFoundException, SQLException, ParseException,
				InstantiationException, IllegalAccessException, InvocationTargetException, JsonProcessingException;
	}

	protected List<E> executeQuery(String query, Query queryExecutor)
			throws ClassNotFoundException, SQLException, ParseException, InstantiationException, IllegalAccessException,
			InvocationTargetException, JsonProcessingException {

		LOGGER.info(query);
		openConnection();
		resultSet = statement.executeQuery(query);
		List<E> out = queryExecutor.doWork(resultSet);
		closeConnection(true);
		return out;
	}

	public static <E> E fromJson(String json, Class<E> classType)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		E session = om.readValue(json, classType);
		return session;
	}

	public static JsonObject toJson(ADTO dto) throws JsonProcessingException {

		ObjectMapper om = new ObjectMapper();
		om.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		String parsedString = om.writeValueAsString(dto);
		return new JsonParser().parse(parsedString).getAsJsonObject();
	}

	protected void executeQueryVoid(String query) throws SQLException {

		LOGGER.info(query);
		openConnection();
		resultSet = statement.executeQuery(query);
		closeConnection(true);
	}

	// protected abstract E returnFirst(List<E> out) throws
	// JsonProcessingException;
	// {
	//
	// if (out != null && !out.isEmpty()) {
	// // LOGGER.info(toJson(out.get(0)));
	// return out.get(0);
	// }
	// return null;
	// }

	public abstract E read(String id);

	public abstract void delete(String id);

	public abstract List<E> list(String query);

	public String create(ADTO e) {

		Session session = DB_CONNECTION_MANAGER.getSession().openSession();
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
		session.close();
		LOGGER.info("Successfully created " + e.toString());
		return e.getId();
	}

	protected E read(String id, Class<?> classType) {
		Session session = DB_CONNECTION_MANAGER.getSession().openSession();
		session.beginTransaction();
		E e = (E) session.load(classType, id);
		LOGGER.info("Successfully read " + e.toString());
		session.close();
		return e;
	}

	protected void delete(String id, Class<?> classType) {
		Session session = DB_CONNECTION_MANAGER.getSession().openSession();
		session.beginTransaction();
		E e = read(id, classType);
		session.delete(e);
		session.getTransaction().commit();
		session.close();
		LOGGER.info("Successfully deleted " + e.toString());
	}

	protected List list(String query, Class<?> classType) {
		Session session = DB_CONNECTION_MANAGER.getSession().openSession();
		List es = session.createNativeQuery(query).setResultTransformer(Transformers.aliasToBean(classType)).list();
		session.close();
		LOGGER.info("Found " + es.size() + " Employees");
		return es;
	}

}
