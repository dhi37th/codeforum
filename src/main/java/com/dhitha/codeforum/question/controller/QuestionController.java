package com.dhitha.codeforum.question.controller;

import com.dhitha.codeforum.common.component.SessionUtil;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
import com.dhitha.codeforum.question.model.Question;
import com.dhitha.codeforum.question.service.QuestionService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/questions")
@Slf4j
public class QuestionController {
  @Autowired QuestionService questionService;

  @Autowired
  SessionUtil sessionUtil;

  // Get question by Id
  @GetMapping(value = "{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Question> getQuestion(@PathVariable("questionId") Long questionId) {
    return questionService
        .getQuestionById(questionId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("Question not found for id: " + questionId));
  }

  //Get all question of session user
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Question>> getAllQuestionOfUser(
      @RequestParam(value = "limit", required = false, defaultValue = "0") int limit,
      @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
    if (sessionUtil.getSessionUser() != null) {
      Long userId = sessionUtil.getSessionUser().getId();
      return questionService
          .getAllCreatedBy(limit, offset, userId)
          .map(ResponseEntity::ok)
          .orElseThrow(
              () -> new ResourceNotFoundException("Question not found for userId: " + userId));
    } else {
      // TODO: change exception
      throw new ResourceNotFoundException("User session not available");
    }
  }

  //Get All questions
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Question>> getAllQuestions(
      @RequestParam(value = "limit", required = false, defaultValue = "0") int limit,
      @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
    return questionService
        .getAllQuestions(limit,offset)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("No questions found"));
  }

  //Delete question
  @DeleteMapping("{questionId}")
  public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
    questionService.deleteQuestion(questionId);
    return ResponseEntity.noContent().build();
  }

  //Save question
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
      @PathVariable Long questionId, @RequestBody Question question) {
    question.setId(questionId);
    return questionService.updateQuestion(question)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("Question not found for id: " + questionId));
  }
}
