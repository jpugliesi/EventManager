package main;

public class GetProfilePictureException extends Exception {

	private int error_code;
	
	public GetProfilePictureException(int code){
		this.error_code = code;
	}
	
	public int getErrorCode(){
		return this.error_code;
	}
	
}
