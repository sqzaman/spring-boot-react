package edu.med.umich.michr.service;

import edu.med.umich.michr.dao.QuestionDao;
import edu.med.umich.michr.domain.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("questionServiceImpl")
public class QuestionServiceImp implements QuestionService {

	private final QuestionDao questionDao;

	@Autowired
	public QuestionServiceImp(@Qualifier("questionDaoImpl") QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	@Override
	public long saveQuestion(Question question) {
		return questionDao.save(question);
	}

	@Override
	public Question getQuestionById(long id) {
		return questionDao.getById(id);
	}

	@Override
	public void updateQuestion(Question question) {
		questionDao.update(question);
	}

	@Override
	public List<Question> getAllQuestions() {
		return questionDao.getAll();
	}

	@Override
	public void deleteQuestion(Question question) {
		questionDao.delete(question);
	}
}
