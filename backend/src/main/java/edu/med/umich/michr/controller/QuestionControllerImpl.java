package edu.med.umich.michr.controller;

import edu.med.umich.michr.domain.Question;
import edu.med.umich.michr.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/questions")
public class QuestionControllerImpl implements QuestionController{

  private final QuestionService questionService;

  @Autowired
  public QuestionControllerImpl(@Qualifier("questionServiceImpl") QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping
  @Override
  public ResponseEntity<Map<String, Long>> post(@Valid @RequestBody Question question) {
    long questionId = questionService.saveQuestion(question);
    
    Map<String, Long> response = Collections.singletonMap("id", questionId);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(questionId).toUri();

    return ResponseEntity.created(location).body(response);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<?> put(@RequestBody Question question, @PathVariable long id) {
    Question questionFetched = questionService.getQuestionById(id);

    if (questionFetched == null) {
      return ResponseEntity.notFound().build();
    }    
    question.setId(id);  
    
    questionService.updateQuestion(question);
    
    return ResponseEntity.noContent().build();
  }

  @Override
  @GetMapping
  public ResponseEntity<List<Question>> getAll() {
    List<Question> questions = questionService.getAllQuestions();

    return ResponseEntity.ok(questions);
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<Question> get(@PathVariable long id) {
    Question question = questionService.getQuestionById(id);

    if (question == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(question);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable long id) {
    Question question = questionService.getQuestionById(id);

    if (question == null) {
      return ResponseEntity.notFound().build();
    }

    questionService.deleteQuestion(question);

    return ResponseEntity.noContent().build();
  }
}
