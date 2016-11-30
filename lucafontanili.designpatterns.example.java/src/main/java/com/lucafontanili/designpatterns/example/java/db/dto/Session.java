package com.lucafontanili.designpatterns.example.java.db.dto;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "SESSION")
public class Session extends ADTO {

	private static final long serialVersionUID = -8682770625960628620L;

	@Column(name = DB_EMPLOYEEID)
	private UUID employeeId;
	@Column(name = DB_CREATIONDATE)
	private Date creationDate;
	@Column(name = DB_VALID)
	private Boolean valid;

	public static final String DB_EMPLOYEEID = "employeeId";
	public static final String DB_CREATIONDATE = "creationDate";
	public static final String DB_VALID = "valid";

	@JsonProperty(DB_EMPLOYEEID)
	public String getEmployeeId() {
		if (this.employeeId == null) {
			return null;
		}
		return this.employeeId.toString();
	}

	@JsonProperty(DB_EMPLOYEEID)
	public void setEmployeeId(String userId) {
		if (userId == null || userId.isEmpty()) {
			this.employeeId = null;
		} else {
			this.employeeId = UUID.fromString(userId);
		}
	}

	@JsonProperty(DB_CREATIONDATE)
	public Date getCreationDate() {
		return this.creationDate;
	}

	@JsonProperty(DB_CREATIONDATE)
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@JsonProperty(DB_VALID)
	public Boolean getValid() {
		return this.valid;
	}

	@JsonProperty(DB_VALID)
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Session() {
		super();
		setEmployeeId("");
		setCreationDate(new Date());
		setValid(true);
	}
}
