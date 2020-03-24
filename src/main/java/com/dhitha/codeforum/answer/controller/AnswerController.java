package com.dhitha.codeforum.answer.controller;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.answer.service.AnswerService;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
@Slf4j
public class AnswerController {
  @Autowired AnswerService answerService;

  // Save answer
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Answer> saveAnswer(
      @PathVariable("questionId") Long questionId, @Valid @RequestBody Answer answer) {
    answer.setQuestionId(questionId);
    Answer returnedAnswer = answerService.addAnswer(answer);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(returnedAnswer.getId())
            .toUri();
    return ResponseEntity.created(uri).body(returnedAnswer);
  }

  // Get all answer for question
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Answer>> getAnswersForQuestion(
      @PathVariable("questionId") Long questionId) {
    return answerService
        .getAllAnswersOfQuestion(questionId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("No answer found for question id: " + questionId));
  }

  // Get answer by id
  @GetMapping(value = "/{answerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Answer> getAnswerById(
      @PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId) {

    return answerService
        .getAnswerById(questionId, answerId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("No answer found for question id: " + questionId));
  }

  // delete answer by id
  @DeleteMapping(value = "/{answerId}")
  public ResponseEntity<Void> deleteAnswer(
      @PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId) {
    answerService.deleteAnswer(answerId, questionId);
    return ResponseEntity.noContent().build();
  }

  // Update answer
  @PutMapping(
      value = "{answerId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Answer> updateAnswer(
      @PathVariable("questionId") Long questionId,
      @PathVariable("answerId") Long answerId,
      @Valid @RequestBody Answer answer) {
    answer.setId(answerId);
    answer.setQuestionId(questionId);
    return answerService.updateAnswer(answer)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Answer not found for id: " + answerId));
  }
}
