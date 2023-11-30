package sessionFramework.responseUser;


public class PermissionResponse {
    private Long idProfile;
    public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	public String getNoModule() {
		return noModule;
	}

	public void setNoModule(String noModule) {
		this.noModule = noModule;
	}

	public String getNoObject() {
		return noObject;
	}

	public void setNoObject(String noObject) {
		this.noObject = noObject;
	}

	public String getNoMethod() {
		return noMethod;
	}

	public void setNoMethod(String noMethod) {
		this.noMethod = noMethod;
	}

	private String noModule;
    private String noObject;
    private String noMethod;
    private String query;

    // Constructor, getters y setters

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
