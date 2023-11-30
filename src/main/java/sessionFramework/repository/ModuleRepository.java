package sessionFramework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sessionFramework.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

}
