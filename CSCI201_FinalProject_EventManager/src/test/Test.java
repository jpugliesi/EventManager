package test;

public class Test {
	
	public boolean testLogin(){
		boolean result = true;
		return result;
	}
	
	public static void main(String [] args){
		Test test = new Test();
		
		//Test Login
		if(test.testLogin()){
			System.out.println("Login: PASSED");
		} else {
			System.out.println("Login: FAILED");
		}
	}
	
	
	
	
	
}
