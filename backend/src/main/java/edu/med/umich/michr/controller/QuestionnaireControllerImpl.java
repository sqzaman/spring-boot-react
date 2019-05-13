package edu.med.umich.michr.controller;

import edu.med.umich.michr.domain.Questionnaire;
import edu.med.umich.michr.service.QuestionnaireService;

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
@RequestMapping(value = "/questionnaire")
public class QuestionnaireControllerImpl implements QuestionnaireController {

	QuestionnaireService questionnaireService;

	@Autowired
	public QuestionnaireControllerImpl(
			@Qualifier("questionnaireServiceImpl") QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}

	@PostMapping
	@Override
	public ResponseEntity<Map<String, Long>> post(@Valid @RequestBody Questionnaire questionnaire) {
		long questionnaireId = questionnaireService.saveQuestionnaire(questionnaire);
		Map<String, Long> response = Collections.singletonMap("id", questionnaireId);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(questionnaireId)
				.toUri();

		return ResponseEntity.created(location).body(response);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@Valid @RequestBody Questionnaire questionnaire, @PathVariable Long id) {

		Questionnaire questionnaireFetched = questionnaireService.getQuestionnaireById(id);

		if (questionnaireFetched == null) {
			return ResponseEntity.notFound().build();
		}

		questionnaireService.updateQuestionnaire(questionnaire, questionnaireFetched);

		return ResponseEntity.noContent().build();
	}

	@Override
	@GetMapping
	public ResponseEntity<List<Questionnaire>> getAll() {
		List<Questionnaire> questionnaires = questionnaireService.getAllQuestionnaires();

		return ResponseEntity.ok(questionnaires);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Questionnaire> get(@PathVariable Long id) {

		Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);

		if (questionnaire == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(questionnaire);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Questionnaire questionnaire = questionnaireService.getQuestionnaireById(id);
		if (questionnaire == null) {
			return ResponseEntity.notFound().build();
		}

		questionnaireService.deleteQuestionnaire(id);

		return ResponseEntity.noContent().build();
	}

}
