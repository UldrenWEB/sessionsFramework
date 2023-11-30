package sessionFramework.requestBody;

import java.util.List;

public class UserSessionForgetData {
	private String user;
	private String email;
	private List<Integer> idQuestions;
	
	public UserSessionForgetData(String user, String email, List<Integer> idQuestions) {
		this.user = user;
		this.email = email;
		this.idQuestions = idQuestions;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public List<Integer> getIdQuestions() {
		return idQuestions;
	}
	public void setIdQuestions(List<Integer> idQuestions) {
		this.idQuestions = idQuestions;
	}
	
	
}
