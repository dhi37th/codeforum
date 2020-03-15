package com.dhitha.codeforum.question.controller;

import com.dhitha.codeforum.common.component.SessionInfo;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
import com.dhitha.codeforum.question.model.Question;
import com.dhitha.codeforum.question.service.QuestionService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/questions")
public class QuestionController {
  @Autowired QuestionService questionService;

  @Autowired SessionInfo sessionInfo;

  @GetMapping(value = "{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Question> getQuestion(@PathVariable("questionId") Long questionId) {
    return questionService
        .getQuestionById(questionId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("Question not found for id: " + questionId));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Question>> getAllQuestionOfUser() {
    if (sessionInfo.getSessionUser() != null) {
      Long userId = sessionInfo.getSessionUser().getId();
      return questionService
          .getAllQuestionsOfUser(userId)
          .map(ResponseEntity::ok)
          .orElseThrow(
              () -> new ResourceNotFoundException("Question not found for userId: " + userId));
    } else {
      //TODO: change exception
      throw new ResourceNotFoundException("User session not available");
    }
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Question>> getAllQuestions() {
    return questionService
        .getAllQuestions()
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("No questions found"));
  }

  @DeleteMapping("{questionId}")
  public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
    questionService.deleteQuestion(questionId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Question> addQuestion(@Valid @RequestBody Question question) {
    question = questionService.addQuestion(question);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(question.getId())
            .toUri();
    return ResponseEntity.created(uri).body(question);
  }

  @PutMapping(
      value = "{questionId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Question> updateQuestion(
      @PathVariable Long questionId, @Valid @RequestBody Question question) {
    return questionService
        .getQuestionById(questionId)
        .map(
            existingQuestion -> {
              question.setId(existingQuestion.getId());
              return ResponseEntity.ok(questionService.updateQuestion(question));
            })
        .orElseThrow(
            () -> new ResourceNotFoundException("Question not found for id: " + questionId));
  }
}
