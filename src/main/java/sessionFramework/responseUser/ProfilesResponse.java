package sessionFramework.responseUser;

import java.util.List;

public class ProfilesResponse {
	private List<String> profiles;
	
	public ProfilesResponse(List<String> profiles) {
		this.profiles = profiles;
	}

	public List<String> getProfile() {
		return profiles;
	}

	public void setProfile(List<String> profile) {
		this.profiles = profiles;
	}
	
}
