package sessionFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sessionFramework.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>{
	
}
