package sessionFramework.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sessionFramework.entity.SeUser;
import sessionFramework.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository up;
	
	public List<SeUser> getAllUsers(){
		return up.findAll();
	}
	
	public Optional<SeUser> getUserById(int id) {
		return up.findById(id);
	}
	
	public SeUser getUserByName(String userName) {
		return up.findUserbyName(userName);
	}
	
	public int reduceAttemps(String userName) {
		return up.reduceAttemps(userName);
	}
	public int resetAttemps(String userName, int attemps) {
		return up.resetAttemps(userName, attemps);
	}
	public int blockUser(String userName) {
		return up.blockUser(userName);
	}
	public int modifiedPass(String newPass, String userName) {
		return up.modifiedPass(newPass, userName);
	}
}
