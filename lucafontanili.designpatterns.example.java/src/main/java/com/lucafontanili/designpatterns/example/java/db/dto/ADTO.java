package com.lucafontanili.designpatterns.example.java.db.dto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucafontanili.designpatterns.example.java.db.DBConstants;

public abstract class ADTO implements Serializable {

	private static final long serialVersionUID = 6262510999987480895L;
	protected static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	protected static final Logger LOGGER = Logger.getLogger(DBConstants.HIBERNATE);

	@Id
	@Column(name = DB_ID)
	protected UUID id;

	public static final String DB_ID = "id";

	@JsonProperty(DB_ID)
	public String getId() {

		return this.id.toString();
	}

	@JsonProperty(DB_ID)
	public void setId(String id) {

		this.id = UUID.fromString(id);
	}

	public ADTO() {
		setId(UUID.randomUUID().toString());
	}

	@Override
	public int hashCode() {

		return 31 * 17 + (this.getId() != null ? this.getId().hashCode() : 0);
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof ADTO))
			return false;
		ADTO dto = (ADTO) obj;
		return dto.getId().equals(this.getId());
	}

	@Deprecated
	public static String getAttributeNames(Class<? extends ADTO> classType) throws IllegalAccessException {

		String attributeNames = "(";
		for (Field f : classType.getFields()) {
			if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())
					&& !f.getName().equals("serialVersionUID")) {
				attributeNames += f.get(classType) + ",";
			}
		}
		attributeNames = attributeNames.substring(0, attributeNames.length() - 1) + ")";
		return attributeNames;
	}

	@Deprecated
	public static String getValues(Class<? extends ADTO> classType) {

		String attributeNames = "(";
		for (Field f : classType.getFields()) {
			if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())
					&& !f.getName().equals("serialVersionUID")) {
				attributeNames += "?,";
			}
		}
		attributeNames = attributeNames.substring(0, attributeNames.length() - 1) + ")";
		return attributeNames;
	}

	@Deprecated
	public static String getAttributeNamesForUpdate(String[] fields) {

		String attributeNames = "(";
		for (String field : fields) {
			attributeNames += field + "=?,";
		}
		attributeNames = attributeNames.substring(0, attributeNames.length() - 1) + ")";
		return attributeNames;
	}

}
