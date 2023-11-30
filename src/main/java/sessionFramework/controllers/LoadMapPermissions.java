package sessionFramework.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sessionFramework.entity.Permission;
import sessionFramework.service.PermissionService;
import sessionFramework.security.Method;
import sessionFramework.security.Module;
import sessionFramework.security.Object;
import sessionFramework.security.Profile;
import sessionFramework.security.Security;

@Component
public class LoadMapPermissions {
	
	@Autowired
	PermissionService ps;
	
	@PostConstruct
	public void loadPermissions() {
		Security instance = Security.getInstance();
		
		List<java.lang.Object[]> permissions = ps.getAllPermission();
		for (java.lang.Object[] row : permissions) {
		    Integer idProfile = (Integer) row[0];
		    String moduleName = (String) row[1];
		    String objectName = (String) row[2];
		    String methodName = (String) row[3];
		    
		    instance.addPermission(idProfile, moduleName, objectName, methodName);
		}
	}
}
