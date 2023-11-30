package sessionFramework.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Object {
	private String name;
	private Map<String, List<String>> methodByObject;
	
	public Object(String name) {
		this.name = name;
		this.methodByObject = new HashMap<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<?, ?> getMethods() {
		return methodByObject;
	}
	public void setMethod(Map<?, ?> methodByObject) {
		this.methodByObject = (Map<String, List<String>>) methodByObject;
	}
	
	
	
	
}
