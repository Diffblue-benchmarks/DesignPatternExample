package com.lucafontanili.designpatterns.example.java.utility.ws;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lucafontanili.designpatterns.example.java.db.DBConstants;

public class Header {

	private static final Logger LOGGER = Logger.getLogger(DBConstants.HIBERNATE);

	private final UUID userSession;

	public Header(HttpServletRequest request) {
		this.userSession = (request.getHeader("FR-session") == null ? null
				: UUID.fromString(request.getHeader("FR-session")));

		printHeaders();
	}

	private void printHeaders() {
		LOGGER.info("userSession: " + this.userSession);
	}

	public UUID getUserSession() {
		return this.userSession;
	}
}
