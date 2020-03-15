package com.dhitha.codeforum.answer.controller;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.answer.service.AnswerService;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
import com.dhitha.codeforum.question.service.QuestionService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class AnswerController {

  @Autowired AnswerService answerService;

  @Autowired QuestionService questionService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Answer> saveAnswer(
      @PathVariable("questionId") Long questionId, @Valid @RequestBody Answer answer) {

    return questionService
        .getQuestionById(questionId)
        .map(
            question -> {
              answer.setQuestion(question);
              Answer returnedAnswer = answerService.addAnswer(answer);
              URI uri =
                  ServletUriComponentsBuilder.fromCurrentRequest()
                      .path("/{id}")
                      .buildAndExpand(returnedAnswer.getId())
                      .toUri();
              return ResponseEntity.created(uri).body(returnedAnswer);
            })
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Answer cannot be added as question not found for id " + questionId));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Answer>> getAnswersForQuestion(
      @PathVariable("questionId") Long questionId) {
    return answerService
        .getAllAnswersOfQuestion(questionId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("No answer found for question id: " + questionId));
  }

  @GetMapping(value = "/{answerId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Answer> getAnswerById(
      @PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId) {

    return answerService
        .getAnswerById(questionId, answerId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("No answer found for question id: " + questionId));
  }

  @DeleteMapping(value = "/{answerId}")
  public ResponseEntity<Void> deleteAnswer(
      @PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId) {
    answerService.deleteAnswer(answerId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(
      value = "{answerId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Answer> updateAnswer(
      @PathVariable("questionId") Long questionId,
      @PathVariable("answerId") Long answerId,
      @Valid @RequestBody Answer answer) {
    answer.setId(answerId);
    answer.setQuestion(questionService.getQuestionById(questionId).orElse(null));
    return Optional.of(answerService.updateAnswer(answer))
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Answer not found for id: " + answerId));
  }
}
