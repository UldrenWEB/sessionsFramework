package sessionFramework.requestBody;

import java.util.List;

public class UserSessionFinalStep {
	private String email;
	private String user;
	private Integer profile;
	
	public UserSessionFinalStep(String user,String email, Integer profile) {
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
	public Integer getProfile() {
		return profile;
	}
	public void setProfile(Integer profile) {
		this.profile = profile;
	}
}
