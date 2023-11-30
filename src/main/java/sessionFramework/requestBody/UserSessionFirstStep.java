package sessionFramework.requestBody;

import java.util.List;

public class UserSessionFirstStep {
	private String email;
	private String user;
	private List<Integer> profile;
	
	public UserSessionFirstStep(String user,String email, List<Integer> profile) {
		this.user = user;
		this.email =  email;
		this.profile = profile;
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
	public List<Integer> getProfile() {
		return profile;
	}
	public void setProfile(List<Integer> profile) {
		this.profile = profile;
	}
	
}
