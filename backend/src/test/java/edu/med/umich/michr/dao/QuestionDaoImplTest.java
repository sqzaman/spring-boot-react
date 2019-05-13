package edu.med.umich.michr.dao;

import edu.med.umich.michr.domain.Question;
import edu.med.umich.michr.dao.QuestionDao;
import edu.med.umich.michr.dao.QuestionDaoImpl;
import edu.med.umich.michr.domain.AnswerOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class QuestionDaoImplTest {

  @Autowired
  private TestEntityManager testEntityManager;

  private QuestionDao dao;

  @BeforeEach
  void setUp() {
    dao = new QuestionDaoImpl(testEntityManager.getEntityManager());
  }

  @AfterEach
  void tearDown() {
    dao = null;
  }

  @Test
  void testSave() {
    Question question = new Question("text");

    Long savedId = dao.save(question);

    Question savedQuestion = dao.getById(savedId);

    assertEquals("text", savedQuestion.getText());
  }

  @Test
  void testUpdate() {
    Question question = new Question("text");

    Long savedId = dao.save(question);

    Question questionToUpdate = new Question(savedId, "new text");

    dao.update(questionToUpdate);

    Question questionFetched = dao.getById(savedId);

    assertEquals(savedId.longValue(), questionFetched.getId());
    assertEquals("new text", questionFetched.getText());
  }

  @Test
  void testGetAll() {
    Question question1 = new Question("text1");
    Question question2 = new Question("text2");

    long id1 = dao.save(question1);
    long id2 = dao.save(question2);

    List<Question> expected = Arrays.asList(new Question(id1, question1.getText()), new Question(id2, question2.getText()));
    List<Question> questions = dao.getAll();

    assertEquals(expected, questions);

  }

  @Test
  void testDelete() {
    Question question = new Question("text");
    long id = dao.save(question);

    assertNotNull(dao.getById(id));

    dao.delete(dao.getById(id));

    assertNull(dao.getById(id));
  }
  
  @Test
  void testMultiselectQuestionSave() {
    String q = " What is you favorite color?";
    Question question = new Question(q);
    question.addToOptions(new AnswerOption("Red"));
    question.addToOptions(new AnswerOption("Green"));
    question.addToOptions(new AnswerOption("White"));
    question.addToOptions(new AnswerOption("Yellow"));

    Long savedId = dao.save(question);

    Question savedQuestion = dao.getById(savedId);

    assertEquals(question.getText(), savedQuestion.getText());
  
    assertEquals(question.getOptions().size(), savedQuestion.getOptions().size());
    // check the option order	 
    assertEquals(question.getOptions().get(0).getText(), savedQuestion.getOptions().get(0).getText());
    assertEquals(question.getOptions().get(1).getText(), savedQuestion.getOptions().get(1).getText());
    assertEquals(question.getOptions().get(2).getText(), savedQuestion.getOptions().get(2).getText());
    assertEquals(question.getOptions().get(3).getText(), savedQuestion.getOptions().get(3).getText());
  }
  
  @Test
  void testMultiselectQuestionUpdate() {
    String q = "What is you favorite color?";
	Question questionToSave = new Question(q);
	questionToSave.addToOptions(new AnswerOption("Red"));
	questionToSave.addToOptions(new AnswerOption("Green"));
	questionToSave.addToOptions(new AnswerOption("White"));
	questionToSave.addToOptions(new AnswerOption("Yellow"));

	Long savedId = dao.save(questionToSave);
	Question savedQuestion = dao.getById(savedId);
	  
	int sizeofSavedQuestionOptions = savedQuestion.getOptions().size();
	AnswerOption firstAnswerOption = savedQuestion.getOptions().get(0);

    String updatedQuestion = "What color do you like most?";	    
    List<AnswerOption> updatedOptions = new ArrayList<>();
    // reordering existing color
    updatedOptions.add(questionToSave.getOptions().get(1));
    updatedOptions.add(questionToSave.getOptions().get(3));
    updatedOptions.add(questionToSave.getOptions().get(0));
    updatedOptions.add(questionToSave.getOptions().get(2));
    
    // adding 2 more in the option
    updatedOptions.add(new AnswerOption("Pink"));
    updatedOptions.add(new AnswerOption("Blue"));

    Question questionToUpdate = new Question(savedId, updatedQuestion, updatedOptions);
    dao.update(questionToUpdate);
    
    // fetch updated question
    Question questionFetchedAfterUpdate = dao.getById(savedId);

    // checked saved and updated question id is same
    assertEquals(savedId.longValue(), questionFetchedAfterUpdate.getId());	    
    
    // check the question text
    assertNotEquals(q, questionFetchedAfterUpdate.getText());
    assertEquals(questionToUpdate.getText(), questionFetchedAfterUpdate.getText());
    
    // check the options size
    assertNotEquals(sizeofSavedQuestionOptions, questionFetchedAfterUpdate.getOptions().size());
    assertEquals(questionToUpdate.getOptions().size(), questionFetchedAfterUpdate.getOptions().size());	    
    
    // check the options order
    assertNotEquals(firstAnswerOption, questionFetchedAfterUpdate.getOptions().get(0));
    assertEquals(firstAnswerOption, questionFetchedAfterUpdate.getOptions().get(2)); // Red is in 3rd position 
    assertEquals(questionToUpdate.getOptions().get(5).getText(), questionFetchedAfterUpdate.getOptions().get(5).getText()); 

  }
  @Test
  void testMultiselectQuestionDelete() {
	  String q = "Which color do you like most?";
		Question question = new Question(q);
		question.addToOptions(new AnswerOption("Red"));
		question.addToOptions(new AnswerOption("Green"));
		question.addToOptions(new AnswerOption("White"));
		question.addToOptions(new AnswerOption("Yellow"));
    long id = dao.save(question);
    Question savedQuestion = dao.getById(id);
    
    assertNotNull(savedQuestion);
    int optionCountBeforeDelete = savedQuestion.getOptions().size();
    assertEquals(question.getOptions().size(), optionCountBeforeDelete);
    
    savedQuestion.removeOption(savedQuestion.getOptions().get(1));
    dao.update(savedQuestion);
    Question savedUpdatedQuestion = dao.getById(id);
    assertNotEquals(optionCountBeforeDelete, savedUpdatedQuestion.getOptions().size());
    
    dao.delete(dao.getById(id));

    assertNull(dao.getById(id));
  }
}