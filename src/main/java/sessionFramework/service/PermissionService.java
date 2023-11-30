package sessionFramework.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sessionFramework.entity.Permission;
import sessionFramework.repository.SecurityRepository;


@Service
public class PermissionService {
	
	@Autowired
	SecurityRepository srp;
	
    public List<Object[]> getAllPermission() {
        return srp.findAllPermissions();
    }
}