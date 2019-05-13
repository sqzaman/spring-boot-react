package edu.med.umich.michr.service;

import java.util.List;
import edu.med.umich.michr.domain.Questionnaire;


public interface QuestionnaireService {
  Long saveQuestionnaire(Questionnaire questionnaire);

  Questionnaire getQuestionnaireById(long id);

  void updateQuestionnaire(Questionnaire updatedQuestionnaire, Questionnaire oldQuestionnaire);

  List<Questionnaire> getAllQuestionnaires();

  void deleteQuestionnaire(Long id);

}
