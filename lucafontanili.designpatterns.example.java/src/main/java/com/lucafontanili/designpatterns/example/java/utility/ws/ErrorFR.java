package com.lucafontanili.designpatterns.example.java.utility.ws;

import com.google.gson.JsonObject;

public class ErrorFR {
	private static final String MESSAGE_STRING = "message";
	private static final String CODE_STRING = "code";
	private final String message;
	private final int code;

	public ErrorFR(String error, int code) {
		this.message = error;
		this.code = code;
	}

	private String getMessage() {
		return this.message;
	}

	private int getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		JsonObject jo = new JsonObject();
		jo.addProperty(MESSAGE_STRING, getMessage());
		jo.addProperty(CODE_STRING, getCode());
		return jo.toString();
	}

}
