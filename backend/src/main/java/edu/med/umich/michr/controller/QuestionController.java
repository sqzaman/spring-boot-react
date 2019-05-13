package edu.med.umich.michr.controller;

import edu.med.umich.michr.domain.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface QuestionController {

  ResponseEntity<Map<String, Long>> post(Question question);
  
  ResponseEntity<?> put(Question question, long id);
  
  ResponseEntity<List<Question>> getAll();

  ResponseEntity<Question> get(long id);

  ResponseEntity<?> delete(long id);
}
