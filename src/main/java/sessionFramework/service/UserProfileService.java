package sessionFramework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sessionFramework.entity.UserProfile;
import sessionFramework.repository.UserProfileRepository;

@Service
public class UserProfileService {
	
	@Autowired
	UserProfileRepository upr;
	
	public List<Integer> getProfileByUser(String userName){
		return upr.findAllProfileByUser(userName);
	}
	
}
