package sessionFramework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sessionFramework.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	@Query(
			value = "SELECT q.id_question, q.de_question, q.res_question FROM question q INNER JOIN se_user sru ON sru.id_user = q.id_user WHERE sru.no_user = :userName LIMIT 2",
			nativeQuery=true
	)
	List<Question> findRamdonQuestionByUser(@Param("userName") String userName);
	
	@Query(
			value="SELECT id_question, de_question, res_question FROM question WHERE id_question=:id",
			nativeQuery=true
	)
	Question findQuestionById(@Param("id")Integer id);
	
	@Query(
			value="SELECT id_question,de_question, res_question FROM question WHERE id_question = :idQuestion",
			nativeQuery=true
	)
	Question findResponseByQuestion(@Param("idQuestion")Integer idQuestion);
}
