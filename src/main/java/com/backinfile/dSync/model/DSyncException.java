package com.backinfile.dSync.model;

public class DSyncException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DSyncException(String message) {
		super(message);
	}

	public DSyncException(Throwable cause) {
		super(cause);
	}

}
