package com.model;

public enum ClientStatus {

	NEW(), ACTIVE(), BLOCKED();

	/**
	 * Get the status to which current one can be changed
	 * 
	 * @return {@link ClientStatus}
	 */
	public ClientStatus nextStatus() {
		switch (this) {
		case ACTIVE:
			return BLOCKED;
		default:
			return ACTIVE;
		}
	}
	
}
