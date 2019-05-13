package edu.med.umich.michr.dao;

import edu.med.umich.michr.domain.Question;
import edu.med.umich.michr.domain.Questionnaire;
import edu.med.umich.michr.domain.AnswerOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class QuestionnaireDaoImplTest {

	@Autowired
	private TestEntityManager testEntityManager;

	private QuestionnaireDao dao;

	@BeforeEach
	void setUp() {
		dao = new QuestionnaireDaoImpl(testEntityManager.getEntityManager());
	}

	@AfterEach
	void tearDown() {
		dao = null;
	}

	@Test
	void testCreateNewQuestionnaire() {

		Question question1 = new Question("Question 1");
		Question question2 = new Question("Which color do you like most");
		question2.setOptions(Arrays.asList(new AnswerOption("Black"), new AnswerOption("Red")));
		Question questio3 = new Question("Have you taken vaccination");
		question2.setOptions(Arrays.asList(new AnswerOption("No"), new AnswerOption("Yes")));

		Questionnaire questionnaire = new Questionnaire("Pliminary checklist for patient",
				Arrays.asList(question1, question2, questio3));
		Long savedId = dao.save(questionnaire);

		Questionnaire savedQuestionnaire = dao.getById(savedId);

		assertEquals(questionnaire.getText(), savedQuestionnaire.getText());
		assertEquals(questionnaire.getQuestions().size(), savedQuestionnaire.getQuestions().size());

		assertEquals(questionnaire.getQuestions().get(1).getOptions().get(1),
				savedQuestionnaire.getQuestions().get(1).getOptions().get(1));

		assertNotEquals(questionnaire.getQuestions().get(1).getOptions().get(1),
				savedQuestionnaire.getQuestions().get(1).getOptions().get(0));

	}

	@Test
	void testUpdateQuestionnaire() {

		String questionnaireText = "MICHR Interview Questionnaire";

		Questionnaire questionnaire = new Questionnaire(questionnaireText,
				Arrays.asList(new Question("What is your name?"),
						new Question("Do you smoke?", Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No"))),
						new Question("Are you taking any medications now?",
								Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No"))),
						new Question("Which color do you like most", Arrays.asList(new AnswerOption("Black"),
								new AnswerOption("Red"), new AnswerOption("Pink"), new AnswerOption("Green")))));
		long savedId = dao.save(questionnaire);
		

		Questionnaire savedQuestionnaire = dao.getById(savedId);

		int questionCountBeforeUpdate = savedQuestionnaire.getQuestions().size();
		
		assertEquals(questionnaireText, savedQuestionnaire.getText());
		assertEquals(questionnaire.getQuestions().size(), savedQuestionnaire.getQuestions().size());

		String updatedQuestionnireText = "Pliminary checklist for patient enrollment";
		Questionnaire updatedQuestionnaire = new Questionnaire(savedId, updatedQuestionnireText, null);
		dao.update(updatedQuestionnaire);
		Questionnaire savedUpdatedQuestionnaire = dao.getById(savedId);
		

		assertEquals(savedId, savedUpdatedQuestionnaire.getId());
		assertEquals(null, savedUpdatedQuestionnaire.getQuestions());

		updatedQuestionnaire = new Questionnaire(savedId, updatedQuestionnireText, Arrays.asList(
				new Question("What is your frst name?"), new Question("What is your last name?"),
				new Question("Are you taking any medications now?",
						Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No"))),
				new Question("Which color do you like most",
						Arrays.asList(new AnswerOption("Black"), new AnswerOption("Red"), new AnswerOption("Pink"),
								new AnswerOption("Green"))),
				new Question("Do you smoke?", Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No"))),
				new Question("Are you taking alcohol", Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No")))

		));

		dao.update(updatedQuestionnaire);
		savedUpdatedQuestionnaire = dao.getById(savedId);

		assertEquals(savedId, savedUpdatedQuestionnaire.getId());
		assertEquals("What is your frst name?", savedUpdatedQuestionnaire.getQuestions().get(0).getText());
		assertNotEquals(questionnaireText, savedUpdatedQuestionnaire.getText());
		assertNotEquals(questionCountBeforeUpdate, savedUpdatedQuestionnaire.getQuestions().size());
		assertEquals("No", savedUpdatedQuestionnaire.getQuestions().get(2).getOptions().get(1).getText());
		assertEquals("Pink", savedUpdatedQuestionnaire.getQuestions().get(3).getOptions().get(2).getText());
	}
	
	 @Test
	  void testDeleteQuestionnaire() {
		 
		 String questionnaireText = "MICHR Interview Questionnaire";

			Questionnaire questionnaire = new Questionnaire(questionnaireText,
					Arrays.asList(new Question("What is your name?"),
							new Question("Do you smoke?", Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No"))),
							new Question("Are you taking any medications now?",
									Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No"))),
							new Question("Which color do you like most", Arrays.asList(new AnswerOption("Black"),
									new AnswerOption("Red"), new AnswerOption("Pink"), new AnswerOption("Green")))));
			long savedId = dao.save(questionnaire);
			

			Questionnaire savedQuestionnaire = dao.getById(savedId);
			assertNotNull(savedQuestionnaire);
			dao.delete(savedQuestionnaire);
			savedQuestionnaire = dao.getById(savedId);
			assertNull(savedQuestionnaire);
			
	  }
	
}