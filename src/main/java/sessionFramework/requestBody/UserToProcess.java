package sessionFramework.requestBody;

import java.util.List;

public class UserToProcess {
	private String module;
	private String object;
	private String method;
	private List<?> params;
	
	
	public List<?> getParams() {
		return params;
	}

	public void setParams(List<?> params) {
		this.params = params;
	}

	public UserToProcess(List<?> params,String module, String object, String method) {
		this.params = params;
		this.module = module;
		this.object = object;
		this.method = method;
	}

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
	
}
