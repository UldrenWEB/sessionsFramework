package sessionFramework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sessionFramework.entity.Permission;

public interface SecurityRepository extends JpaRepository<Permission, Integer> {
	@Query(
			value="SELECT up.id_profile, mdl.no_module, obj.no_object, mth.no_method FROM permission pp INNER JOIN bo_method mth ON mth.id_method = pp.id_method INNER JOIN bo_object obj ON obj.id_object = mth.id_object INNER JOIN module mdl ON mdl.id_module = obj.id_module INNER JOIN profile prf ON prf.id_profile = pp.id_profile INNER JOIN user_profile up ON up.id_profile = prf.id_profile",
			nativeQuery=true
	)
	List<Object[]> findAllPermissions();
}
