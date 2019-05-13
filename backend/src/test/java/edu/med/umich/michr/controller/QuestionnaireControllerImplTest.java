package edu.med.umich.michr.controller;

import edu.med.umich.michr.domain.Questionnaire;
import edu.med.umich.michr.service.QuestionnaireService;

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
class QuestionnaireControllerImplTest {

	@Mock
	private QuestionnaireService questionnaireService;

	private QuestionnaireController controller;
	static final String QUESTIONNAIRE_TEXT = "MICHR Interview Questionnaire";

	@BeforeEach
	void setUp() {
		controller = new QuestionnaireControllerImpl(questionnaireService);
	}

	@AfterEach
	void tearDown() {
		controller = null;
	}

	@Test
	void testPost() {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/questionnaire/");
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		long questionId = 1L;

		Questionnaire questionnaire = new Questionnaire(QUESTIONNAIRE_TEXT);

		when(questionnaireService.saveQuestionnaire(questionnaire)).thenReturn(questionId);

		ResponseEntity<Map<String, Long>> actual = controller.post(questionnaire);
		assertEquals(actual.getBody().get("id"), (Long) questionId);
		assertEquals(actual.getStatusCode(), HttpStatus.CREATED);
		assertEquals("http://localhost/questionnaire/1", actual.getHeaders().getLocation().toString());
	}
	

	@Test
	void testGetById_QuestionnaireNotExists() {
		long questionnireId = 1L;

		when(questionnaireService.getQuestionnaireById(questionnireId)).thenReturn(null);

		ResponseEntity<?> actual = controller.get(questionnireId);

		assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
	}

	@Test
	void testGetById_QuestionnaireExists() {
		long questionnireId = 1L;
		Questionnaire questionnaire = new Questionnaire(questionnireId, QUESTIONNAIRE_TEXT);

		when(questionnaireService.getQuestionnaireById(questionnireId)).thenReturn(questionnaire);

		ResponseEntity<?> actual = controller.get(questionnireId);

		assertEquals(questionnaire, actual.getBody());
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

	 @Test
	  void testPut_QuestionnaireNotExist() {
	    long questionnaireId = 1L;

	    when(questionnaireService.getQuestionnaireById(questionnaireId)).thenReturn(null);

	    ResponseEntity<?> actual = controller.put(new Questionnaire(QUESTIONNAIRE_TEXT), questionnaireId);

	    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
	  }
	 
	 @Test
	  void testPut_QuestionnaireExists() {
	    long questionnaireId = 1L;
	    Questionnaire questionnaireFetched = new Questionnaire(questionnaireId, QUESTIONNAIRE_TEXT);
	    Questionnaire questionnaire = new Questionnaire(String.format("%s%s", QUESTIONNAIRE_TEXT, ""));

	    ArgumentCaptor<Questionnaire> questionnaireArgumentCaptor = ArgumentCaptor.forClass(Questionnaire.class);

	    when(questionnaireService.getQuestionnaireById(questionnaireId)).thenReturn(questionnaireFetched);


	    ResponseEntity<?> actual = controller.put(questionnaire, questionnaireId);
	    assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());

	    verify(questionnaireService, times(1)).updateQuestionnaire(questionnaireArgumentCaptor.capture(), questionnaireArgumentCaptor.capture());

	    Questionnaire questionnaireToUpdate = questionnaireArgumentCaptor.getValue();
	    assertEquals(questionnaireId, questionnaireToUpdate.getId());
	    assertEquals(questionnaire.getText(), questionnaireToUpdate.getText());
	  }
	 
	 @Test
	  void testGetAllQuestionnaire() {
	    List<Questionnaire> expectedList = new ArrayList<>();
	    when(questionnaireService.getAllQuestionnaires()).thenReturn(expectedList);

	    ResponseEntity<List<Questionnaire>> actual = controller.getAll();

	    assertEquals(expectedList, actual.getBody());
	    assertEquals(HttpStatus.OK, actual.getStatusCode());
	  }
	 @Test
	  void testDelete_QuestionnaireNotExist() {
	    long questionnaireId = 1L;

	    when(questionnaireService.getQuestionnaireById(questionnaireId)).thenReturn(null);

	    ResponseEntity<?> actual = controller.delete(questionnaireId);

	    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
	  }

	  @Test
	  void testDelete_QuestionnaireExists() {
	    long questionnaireId = 1L;

	    Questionnaire questionnaire = new Questionnaire(questionnaireId, QUESTIONNAIRE_TEXT);
	    when(questionnaireService.getQuestionnaireById(questionnaireId)).thenReturn(questionnaire);

	    ResponseEntity<?> actual = controller.delete(questionnaireId);

	    assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
	    verify(questionnaireService, times(1)).deleteQuestionnaire(questionnaireId);
	  } 
	
}