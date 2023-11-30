package sessionFramework.requestBody;

public class UserKeyChange {
	private String newPass;
	private String pass;
	
	public UserKeyChange(String newPass, String pass) {
		this.newPass = newPass;
		this.pass = pass;
	}

	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
