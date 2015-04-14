package main;

public class GetAdminsException extends Exception {
	
	private int error_code;
	
	public GetAdminsException(int code){
		this.error_code = code;
	}
	
	public int getErrorCode(){
		return this.error_code;
	}

}
