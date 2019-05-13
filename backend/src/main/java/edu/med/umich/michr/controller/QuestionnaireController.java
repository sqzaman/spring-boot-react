package edu.med.umich.michr.controller;

import org.springframework.http.ResponseEntity;

import edu.med.umich.michr.domain.Questionnaire;

import java.util.List;
import java.util.Map;

public interface QuestionnaireController {

  ResponseEntity<Map<String, Long>> post(Questionnaire questionnaire);

  ResponseEntity<?> put(Questionnaire questionnaire, Long id);

  ResponseEntity<List<Questionnaire>> getAll();

  ResponseEntity<Questionnaire> get(Long id);

  ResponseEntity<?> delete(Long id);
}
