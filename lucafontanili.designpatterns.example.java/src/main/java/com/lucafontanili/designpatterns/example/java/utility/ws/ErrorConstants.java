package com.lucafontanili.designpatterns.example.java.utility.ws;

public class ErrorConstants {
	public static final int INTERNAL_SERVER_ERROR_CODE = 5000;
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "C'e' stato qualche problema nel contattare il server. Riprova più tardi";

	public static final int EMPLOYEE_AUTHENTICATION_FORBIDDEN_CODE = 4030;
	public static final String EMPLOYEE_AUTHENTICATION_FORBIDDEN_MESSAGE = "Credenziali di accesso non valide";
	public static final int EMPLOYEE_AUTHENTICATION_USER_NOT_FOUND_CODE = 4031;

	public static final int EMPLOYEE_REGISTRATION_EMAIL_ALREADY_USED_CODE = 4032;
	public static final String EMPLOYEE_REGISTRATION_EMAIL_ALREADY_USED_MESSAGE = "Questa e-mail e' gia' stata utilizzata";
	public static final int EMPLOYEE_REGISTRATION_USERNAME_ALREADY_USED_CODE = 4033;
	public static final String EMPLOYEE_REGISTRATION_USERNAME_ALREADY_USED_MESSAGE = "Questo userName e' gia' stato utilizzato";

	public static final int EMPLOYEE_ACTIVATION_INVALID_HASH_CODE = 4010;
	public static final String EMPLOYEE_ACTIVATION_INVALID_HASH_MESSAGE = "Attivazione non riuscita";
	public static final int EMPLOYEE_ACTIVATION_HASH_NOT_MATCHED_CODE = 4010;

	public static final int BUSINESSTRIP_CREATE_INVALID_BODY_CODE = 4000;
	public static final String BUSINESSTRIP_CREATE_INVALID_BODY_MESSAGE = "Invalid request";

}
