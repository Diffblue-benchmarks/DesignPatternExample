package com.lucafontanili.designpatterns.example.java.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.lucafontanili.designpatterns.example.java.db.dto.Employee;

public class DBConnectionManager {

	private static volatile DBConnectionManager INSTANCE;

	private static final Logger LOGGER = Logger.getLogger(DBConstants.HIBERNATE);
	private static final Properties PROPERTIES = new Properties();

	private static final BasicDataSource dataSource = new BasicDataSource();
	private final SessionFactory sessionFactory;

	static {
		try (InputStream is = new FileInputStream(System.getProperty(DBConstants.CONFIGURATION_PATH) + File.separator
				+ DBConstants.CONFIGURATION_FILE_NAME)) {
			PROPERTIES.loadFromXML(is);
			dataSource.setDriverClassName(PROPERTIES.getProperty(DBConstants.DB_DRIVERCLASSNAME));
			dataSource.setUrl(PROPERTIES.getProperty(DBConstants.DB_URL));
			dataSource.setUsername(PROPERTIES.getProperty(DBConstants.DB_USERNAME));
			dataSource.setPassword(PROPERTIES.getProperty(DBConstants.DB_PASSWORD));
		} catch (IOException e) {
			LOGGER.error("Unable to read configurations", e);
		}
	}

	@Deprecated
	public static Connection connect() throws SQLException {

		return dataSource.getConnection();
	}

	private DBConnectionManager() {
		sessionFactory = getSessionFactory();
	}

	public static DBConnectionManager getInstance() {
		DBConnectionManager result = INSTANCE;
		if (result == null) {
			synchronized (DBConnectionManager.class) {
				result = INSTANCE;
				if (result == null) {
					result = INSTANCE = new DBConnectionManager();
				}

			}
		}
		return result;
	}

	private static SessionFactory getSessionFactory() {

		Configuration configuration = new Configuration().addClass(Employee.class)
				.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect")
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url",
						"jdbc:mysql://localhost/hibernate?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
				.setProperty("hibernate.connection.username", "hibernate")
				.setProperty("hibernate.connection.password", "hibernatepwd")
				.setProperty("hibernate.connection.pool_size", "1").setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.hbm2ddl.auto", "update");

		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public SessionFactory getSession() {
		return this.sessionFactory;
	}

}
