package edu.med.umich.michr.service;

import edu.med.umich.michr.dao.QuestionnaireDao;
import edu.med.umich.michr.domain.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.transaction.Transactional;


@Transactional
@Service("questionnaireServiceImpl")
public class QuestionnaireServiceImp implements QuestionnaireService {

	private final QuestionnaireDao questionnaireDao;

	@Autowired
	public QuestionnaireServiceImp(@Qualifier("questionnaireDaoImpl") QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}

	@Override
	public Long saveQuestionnaire(Questionnaire questionnaire) {
		return questionnaireDao.save(questionnaire);
	}

	@Override
	public Questionnaire getQuestionnaireById(long id) {
		return questionnaireDao.getById(id);	
	}

	@Override
	public void updateQuestionnaire(Questionnaire updatedQuestionnaire, Questionnaire oldQuestionnaire) {
		updatedQuestionnaire.setId(oldQuestionnaire.getId());
		questionnaireDao.update(updatedQuestionnaire);
	}

	@Override
	public List<Questionnaire> getAllQuestionnaires() {
		return questionnaireDao.getAll();
	}

	@Override
	public void deleteQuestionnaire(Long id) {
		Questionnaire questionnaire = questionnaireDao.getById(id);
		questionnaireDao.delete(questionnaire);
	}

}
