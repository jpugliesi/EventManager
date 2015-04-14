package main;

public class GetEventException extends Exception {
	
	private int error_code;
	
	public GetEventException(int code){
		this.error_code = code;
	}
	
	public int getErrorCode(){
		return this.error_code;
	}

}
