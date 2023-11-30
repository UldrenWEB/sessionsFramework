package sessionFramework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sessionFramework.entity.Question;
import sessionFramework.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	QuestionRepository qp;
	
	
	public List<Question> getAllQuestionByUser(String userName){
		return qp.findRamdonQuestionByUser(userName);
	}
	public Question getResponseByQuestion(Integer idQuestion) {
		return qp.findResponseByQuestion(idQuestion);
	}
	public Question getQuestionById(Integer id) {
		return qp.findQuestionById(id);
	}
}
