package sessionFramework.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Module {
	private String name;
	private Map<String, Map<String, List<String>>> objectByModule;
	
	public Module(String name) {
		this.name = name;
		this.objectByModule = new HashMap<String, Map<String, List<String>>>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Map<String, List<String>>> getObjects() {
		return objectByModule;
	}
	public void setObjects(Map<String, Map<String, List<String>>> objectByModule) {
		this.objectByModule = objectByModule;
	}
	
	
}
