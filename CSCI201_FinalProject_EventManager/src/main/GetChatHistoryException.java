package main;

public class GetChatHistoryException extends Exception {

	private int error_code;
	
	public GetChatHistoryException(int code){
		this.error_code = code;
	}
	
	public int getErrorCode(){
		return this.error_code;
	}
	
}
