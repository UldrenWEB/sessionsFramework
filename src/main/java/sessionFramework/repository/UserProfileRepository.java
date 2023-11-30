package sessionFramework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sessionFramework.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	
	@Query(
			value="SELECT id_profile FROM user_profile up INNER JOIN se_user sru ON sru.id_user = up.id_user WHERE no_user = :userName",
			nativeQuery=true
	)
	List<Integer> findAllProfileByUser(@Param("userName")String userName);
}
