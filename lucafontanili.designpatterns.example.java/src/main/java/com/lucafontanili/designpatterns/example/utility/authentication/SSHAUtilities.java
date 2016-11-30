package com.lucafontanili.designpatterns.example.utility.authentication;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.lucafontanili.designpatterns.example.java.db.DBConstants;

public class SSHAUtilities {

	private static final String ZEROS = "%0";
	private static final String D = "d";
	public static final int ITERATIONS = 1000;

	public static UserSecrets hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATIONS, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(DBConstants.SECRET_KEY_FACTOR);
		byte[] hash = skf.generateSecret(spec).getEncoded();
		UserSecrets userSecrets = new UserSecrets(toHex(salt), toHex(hash), ITERATIONS);
		return userSecrets;
	}

	public static String hash(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] chars = password.toCharArray();
		byte[] s = fromHex(salt);

		PBEKeySpec spec = new PBEKeySpec(chars, s, ITERATIONS, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(DBConstants.SECRET_KEY_FACTOR);
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return toHex(hash);
	}

	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance(DBConstants.SHA1);
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format(ZEROS + paddingLength + D, 0) + hex;
		}
		return hex;
	}

	public static boolean validatePassword(String originalPassword, UserSecrets userSecrets)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = userSecrets.getIterations();
		byte[] salt = fromHex(userSecrets.getSalt());
		byte[] hash = fromHex(userSecrets.getHash());

		PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(DBConstants.SECRET_KEY_FACTOR);
		byte[] testHash = skf.generateSecret(spec).getEncoded();

		int diff = hash.length ^ testHash.length;
		for (int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
	}

	private static byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
