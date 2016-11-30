package com.lucafontanili.designpatterns.example.java.utility.ws;

import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;

public class ServiceResponse {
	private JsonObject body;
	private Response response;

	public JsonObject getBody() {
		return this.body;
	}

	public void setBody(JsonObject body) {
		this.body = body;
	}

	public Response getResponse() {
		return this.response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return new StringBuilder(128).append(getBody()).toString();
	}
}
