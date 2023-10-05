package com.poscodx.mysite.exception;

public class FileUploadSeviceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadSeviceException(String message) {
		super(message);
	}
	
	public FileUploadSeviceException() {
		super("FileUploadService Exception Thrown");
	}
}
