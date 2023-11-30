package sessionFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import sessionFramework.entity.SeUser;

@Repository
public interface UserRepository extends JpaRepository<SeUser, Integer>{

	@Query(
			value="SELECT * FROM se_user WHERE no_user = :userName",
			nativeQuery=true
	)
	SeUser findUserbyName(@Param("userName")String userName);
	
	@Transactional
	@Modifying
	@Query(
			value="UPDATE se_user set at_user = at_user - 1 WHERE no_user = :userName",
			nativeQuery=true)
	int reduceAttemps(@Param("userName")String userName);
	
	@Transactional
	@Modifying
	@Query(
			value="UPDATE se_user set at_user = :attemps WHERE no_user = :userName",
			nativeQuery=true)
	int resetAttemps(@Param("userName")String userName, @Param("attemps") int attemps);
	
	@Transactional
	@Modifying
	@Query(
			value="UPDATE se_user set bl_user = true WHERE no_user = :userName",
			nativeQuery=true)
	int blockUser(@Param("userName")String userName);
	
	@Transactional
	@Modifying
	@Query(
			value="UPDATE se_user set pw_user = :newPass WHERE no_user = :userName",
			nativeQuery=true)
	int modifiedPass(@Param("newPass")String newPass, @Param("userName")String userName);    
	
}
