package com.lucafontanili.designpatterns.example.utility.authentication;

public class UserSecrets {
	private String salt;
	private String hash;
	private int iterations;

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getIterations() {
		return this.iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public UserSecrets(String salt, String hash, int iterations) {
		setSalt(salt);
		setHash(hash);
		setIterations(iterations);
	}

	@Override
	public String toString() {
		return getIterations() + ":" + getSalt() + ":" + getHash();
	}
}