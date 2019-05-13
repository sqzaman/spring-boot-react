package edu.med.umich.michr.service;

import edu.med.umich.michr.dao.QuestionnaireDao;
import edu.med.umich.michr.domain.AnswerOption;
import edu.med.umich.michr.domain.Question;
import edu.med.umich.michr.domain.Questionnaire;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionnaireServiceImpTest {

	@Mock
	private QuestionnaireDao questionnaireDao;

	private QuestionnaireService service;

	Questionnaire questionnaire;

	@BeforeEach
	void setUp() {
		service = new QuestionnaireServiceImp(questionnaireDao);
		this.questionnaire = new Questionnaire("MICHR Interview Questionnaire",
				Arrays.asList(new Question("What is your name?"),
						new Question("Do you smoke?", Arrays.asList(new AnswerOption("Yes"), new AnswerOption("No")))));
	}

	@AfterEach
	void tearDown() {
		service = null;
	}

	@Test
	void testSaveQuestionnaire() {
		long savedId = 1L;

		when(questionnaireDao.save(this.questionnaire)).thenReturn(savedId);

		long actual = service.saveQuestionnaire(this.questionnaire);
		assertEquals(savedId, actual);
	}

	@Test
	void testGetQuestionnaireById() {
		long questionnaireId = 1L;
		Questionnaire questionnaire = new Questionnaire(questionnaireId, "text");

		when(questionnaireDao.getById(questionnaireId)).thenReturn(questionnaire);

		Questionnaire actual = service.getQuestionnaireById(questionnaireId);

		assertEquals(questionnaire, actual);
	}

	@Test
	void testUpdateQuestionnaire() {
		Questionnaire questionnaire = new Questionnaire(1L, "text");
		service.updateQuestionnaire(questionnaire, questionnaire);

		verify(questionnaireDao, times(1)).update(questionnaire);
	}

	@Test
	void testGetAllQuestionnaires() {
		List<Questionnaire> expected = new ArrayList<>();

		when(questionnaireDao.getAll()).thenReturn(expected);

		List<Questionnaire> actual = service.getAllQuestionnaires();

		assertEquals(expected, actual);
	}

	@Test
	void testDeleteQuestionnaire() {
		long savedId = 1L;

		when(questionnaireDao.save(this.questionnaire)).thenReturn(savedId);
		long actual = service.saveQuestionnaire(this.questionnaire);

		assertEquals(savedId, actual);

		service.deleteQuestionnaire(actual);

		assertNull(service.getQuestionnaireById(actual));
	}

}