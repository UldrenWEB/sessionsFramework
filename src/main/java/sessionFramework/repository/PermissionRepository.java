package sessionFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sessionFramework.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    
}
