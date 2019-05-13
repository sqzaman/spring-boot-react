package edu.med.umich.michr.controller;

import edu.med.umich.michr.domain.Question;
import edu.med.umich.michr.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionControllerImplTest {

  @Mock
  private QuestionService questionService;

  private QuestionController controller;

  @BeforeEach
  void setUp() {
    controller = new QuestionControllerImpl(questionService);
  }

  @AfterEach
  void tearDown() {
    controller = null;
  }

  @Test
  void testPost() {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/questions/");
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    long questionId = 1L;
    Question question = new Question("text");
    when(questionService.saveQuestion(question)).thenReturn(questionId);

    ResponseEntity<Map<String, Long>> actual = controller.post(question);
    assertEquals(actual.getBody().get("id"), (Long)questionId);
    assertEquals(actual.getStatusCode(), HttpStatus.CREATED);
    assertEquals("http://localhost/questions/1", actual.getHeaders().getLocation().toString());
  }

  @Test
  void testPut_QuestionNotExist() {
    long questionId = 1L;

    when(questionService.getQuestionById(questionId)).thenReturn(null);

    ResponseEntity<?> actual = controller.put(new Question("text"), questionId);

    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
  }

  @Test
  void testPut_QuestionExists() {
    long questionId = 1L;
    Question questionFetched = new Question(questionId, "text");
    Question question = new Question("new text");

    ArgumentCaptor<Question> questionArgumentCaptor = ArgumentCaptor.forClass(Question.class);

    when(questionService.getQuestionById(questionId)).thenReturn(questionFetched);


    ResponseEntity<?> actual = controller.put(question, questionId);
    assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());

    verify(questionService, times(1)).updateQuestion(questionArgumentCaptor.capture());

    Question questionToUpdate = questionArgumentCaptor.getValue();
    assertEquals(questionId, questionToUpdate.getId());
    assertEquals(question.getText(), questionToUpdate.getText());
  }

  @Test
  void testGetAll() {
    List<Question> expectedList = new ArrayList<>();
    when(questionService.getAllQuestions()).thenReturn(expectedList);

    ResponseEntity<List<Question>> actual = controller.getAll();

    assertEquals(expectedList, actual.getBody());
    assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  void testGetById_QuestionNotExistj() {
    long questionId = 1L;

    when(questionService.getQuestionById(questionId)).thenReturn(null);

    ResponseEntity<Question> actual = controller.get(questionId);

    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
  }

  @Test
  void testGetById_QuestionExists() {
    long questionId = 1L;
    Question question = new Question(questionId, "text");

    when(questionService.getQuestionById(questionId)).thenReturn(question);

    ResponseEntity<Question> actual = controller.get(questionId);

    assertEquals(question, actual.getBody());
    assertEquals(HttpStatus.OK, actual.getStatusCode());
  }

  @Test
  void testDelete_QuestionNotExist() {
    long questionId = 1L;

    when(questionService.getQuestionById(questionId)).thenReturn(null);

    ResponseEntity<?> actual = controller.delete(questionId);

    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
  }

  @Test
  void testDelete_QuestionExists() {
    long questionId = 1L;

    Question question = new Question(questionId, "text");
    when(questionService.getQuestionById(questionId)).thenReturn(question);

    ResponseEntity<?> actual = controller.delete(questionId);

    assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
    verify(questionService, times(1)).deleteQuestion(question);
  }
}