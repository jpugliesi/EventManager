package main;

public class LoginException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private int error_code;
	
	public LoginException(int code){
		super();
		this.error_code = code;
		
	}
	
	public int getErrorCode(){
		return error_code;
	}

}
