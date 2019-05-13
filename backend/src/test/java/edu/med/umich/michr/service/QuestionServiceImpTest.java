package edu.med.umich.michr.service;

import edu.med.umich.michr.dao.QuestionDao;
import edu.med.umich.michr.domain.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImpTest {

  @Mock
  private QuestionDao questionDao;

  private QuestionService service;

  @BeforeEach
  void setUp() {
    service = new QuestionServiceImp(questionDao);
  }

  @AfterEach
  void tearDown() {
    service = null;
  }

  @Test
  void testSaveQuestion() {
    long savedId = 1L;
    Question question = new Question("text");
    when(questionDao.save(question)).thenReturn(savedId);

    long actual = service.saveQuestion(question);
    assertEquals(savedId, actual);
  }

  @Test
  void testGetQuestionById() {
    long questionId = 1L;
    Question question = new Question(questionId, "text");

    when(questionDao.getById(questionId)).thenReturn(question);

    Question actual = service.getQuestionById(questionId);

    assertEquals(question, actual);
  }

  @Test
  void testUpdateQuestion() {
    Question question = new Question(1L, "text");
    service.updateQuestion(question);

    verify(questionDao, times(1)).update(question);
  }

  @Test
  void testGetAllQuestions() {
    List<Question> expected = new ArrayList<>();

    when(questionDao.getAll()).thenReturn(expected);

    List<Question> actual = service.getAllQuestions();

    assertEquals(expected, actual);
  }

  @Test
  void testDeleteQuestion() {
    Question question = new Question();
    service.deleteQuestion(question);

    verify(questionDao, times(1)).delete(question);
  }
}