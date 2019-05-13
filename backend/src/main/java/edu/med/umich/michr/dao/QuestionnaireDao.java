package edu.med.umich.michr.dao;

import java.util.List;

import edu.med.umich.michr.domain.Questionnaire;

public interface QuestionnaireDao {
  Long save(Questionnaire questionnaire);

  Questionnaire getById(long id);

  void update(Questionnaire questionnaire);

  List<Questionnaire> getAll();

  void delete(Questionnaire questionnaire);
}
