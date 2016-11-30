package com.lucafontanili.designpatterns.example.java.db.dto;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucafontanili.designpatterns.example.utility.authentication.SSHAUtilities;
import com.lucafontanili.designpatterns.example.utility.authentication.UserSecrets;

@Entity
@Table(name = "EMPLOYEE")
public class Employee extends ADTO {

	private static final long serialVersionUID = -6305012678620391647L;
	@Column(name = DB_FIRSTNAME)
	private String firstName;
	@Column(name = DB_LASTNAME)
	private String lastName;
	@Column(name = DB_FISCALCODE)
	private String fiscalCode;
	@Type(type = "true_false")
	@Column(name = DB_SUPERVISOR)
	private boolean supervisor;
	@Column(name = DB_COMPANYID)
	private UUID companyId;
	@Column(name = DB_BIRTHDATE)
	private Date birthDate;
	@Column(name = DB_EMAIL)
	private String email;
	@Column(name = DB_USERNAME)
	private String username;
	@Column(name = DB_SALT)
	private String salt;
	@Column(name = DB_HASH)
	private String hash;
	@Type(type = "true_false")
	@Column(name = DB_ACTIVE)
	private boolean active;
	@Column(name = DB_REGISTRATIONDATE)
	private Date registrationDate;
	@Column(name = DB_ACTIVATIONDATE)
	private Date activationDate;

	public static final String DB_FIRSTNAME = "firstName";
	public static final String DB_LASTNAME = "lastName";
	public static final String DB_FISCALCODE = "fiscalCode";
	public static final String DB_SUPERVISOR = "supervisor";
	public static final String DB_COMPANYID = "companyId";
	public static final String DB_BIRTHDATE = "birthDate";
	public static final String DB_EMAIL = "email";
	public static final String DB_USERNAME = "username";
	public static final String DB_SALT = "salt";
	public static final String DB_HASH = "hash";
	public static final String DB_ACTIVE = "active";
	public static final String DB_REGISTRATIONDATE = "registrationDate";
	public static final String DB_ACTIVATIONDATE = "activationDate";

	public Employee() {
		super();
		setFirstName("");
		setLastName("");
		setFiscalCode("");
		setSupervisor(false);
		setCompanyId("");
		setBirthDate(new Date(0L));
		setEmail("");
		setUsername("");
		setSalt("");
		setHash("");
		setActive(false);
		setRegistrationDate(new Date());
		setActivationDate(new Date(0L));
	}

	@Column(name = DB_FIRSTNAME)
	@JsonProperty(DB_FIRSTNAME)
	public String getFirstName() {
		return this.firstName;
	}

	@Column(name = DB_FIRSTNAME)
	@JsonProperty(DB_FIRSTNAME)
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = DB_LASTNAME)
	@JsonProperty(DB_LASTNAME)
	public String getLastName() {
		return this.lastName;
	}

	@Column(name = DB_LASTNAME)
	@JsonProperty(DB_LASTNAME)
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = DB_FISCALCODE)
	@JsonProperty(DB_FISCALCODE)
	public String getFiscalCode() {
		return this.fiscalCode;
	}

	@Column(name = DB_FISCALCODE)
	@JsonProperty(DB_FISCALCODE)
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	@Type(type = "true_false")
	@Column(name = DB_SUPERVISOR)
	@JsonProperty(DB_SUPERVISOR)
	public Boolean getSupervisor() {
		return this.supervisor;
	}

	@Column(name = DB_SUPERVISOR)
	@JsonProperty(DB_SUPERVISOR)
	public void setSupervisor(Boolean supervisor) {
		this.supervisor = supervisor;
	}

	@Column(name = DB_COMPANYID)
	@JsonProperty(DB_COMPANYID)
	public String getCompanyId() {
		if (this.companyId == null) {
			return null;
		}
		return this.companyId.toString();
	}

	@Column(name = DB_COMPANYID)
	@JsonProperty(DB_COMPANYID)
	public void setCompanyId(String companyId) {
		if (companyId == null || companyId.isEmpty()) {
			this.companyId = null;
		} else {
			this.companyId = UUID.fromString(companyId);
		}
	}

	@Column(name = DB_BIRTHDATE)
	@JsonProperty(DB_BIRTHDATE)
	public Date getBirthDate() {
		return this.birthDate;
	}

	@Column(name = DB_BIRTHDATE)
	@JsonProperty(DB_BIRTHDATE)
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = DB_EMAIL)
	@JsonProperty(DB_EMAIL)
	public String getEmail() {
		return this.email;
	}

	@Column(name = DB_EMAIL)
	@JsonProperty(DB_EMAIL)
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = DB_USERNAME)
	@JsonProperty(DB_USERNAME)
	public String getUsername() {
		return this.username;
	}

	@Column(name = DB_USERNAME)
	@JsonProperty(DB_USERNAME)
	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = DB_SALT)
	@JsonProperty(DB_SALT)
	public String getSalt() {
		return this.salt;
	}

	@Column(name = DB_SALT)
	@JsonProperty(DB_SALT)
	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = DB_HASH)
	@JsonProperty(DB_HASH)
	public String getHash() {
		return this.hash;
	}

	@Column(name = DB_HASH)
	@JsonProperty(DB_HASH)
	public void setHash(String hash) {
		this.hash = hash;
	}

	@Type(type = "true_false")
	@Column(name = DB_ACTIVE)
	@JsonProperty(DB_ACTIVE)
	public Boolean getActive() {
		return active;
	}

	@Column(name = DB_ACTIVE)
	@JsonProperty(DB_ACTIVE)
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = DB_REGISTRATIONDATE)
	@JsonProperty(DB_REGISTRATIONDATE)
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Column(name = DB_REGISTRATIONDATE)
	@JsonProperty(DB_REGISTRATIONDATE)
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Column(name = DB_ACTIVATIONDATE)
	@JsonProperty(DB_ACTIVATIONDATE)
	public Date getActivationDate() {
		return activationDate;
	}

	@Column(name = DB_ACTIVATIONDATE)
	@JsonProperty(DB_ACTIVATIONDATE)
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	@JsonIgnore
	public void setSecrets(UserSecrets secrets) {
		setSalt(secrets.getSalt());
		setHash(secrets.getHash());
	}

	@JsonIgnore
	public UserSecrets getSecrets() {
		return new UserSecrets(getSalt(), getHash(), SSHAUtilities.ITERATIONS);
	}

	@Override
	public String toString() {
		return new StringBuilder(32).append("[").append(getId()).append(",]").toString();
	}
}
