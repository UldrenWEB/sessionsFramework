package sessionFramework.responseUser;

import lombok.Getter;
import lombok.Setter;


public class ErrorResponse {
    private String error;
    
    public ErrorResponse(String error) {
        this.error = error;
    }

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
    
}
