package aditya.messenger.exception;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8701610912450952377L;
	
	public DataNotFoundException(String message){
		super(message);
	}
}
