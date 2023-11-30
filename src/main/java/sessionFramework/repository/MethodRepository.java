package sessionFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sessionFramework.entity.BoMethod;

@Repository
public interface MethodRepository extends JpaRepository<BoMethod, Integer> {
	
}
