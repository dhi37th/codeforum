package com.dhitha.codeforum.comment.controller;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.answer.service.AnswerService;
import com.dhitha.codeforum.comment.model.Comment;
import com.dhitha.codeforum.comment.service.CommentService;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
import com.dhitha.codeforum.question.model.Question;
import com.dhitha.codeforum.question.service.QuestionService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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
@RequestMapping
public class CommentController {

  @Autowired CommentService commentService;

  @Autowired QuestionService questionService;

  @Autowired AnswerService answerService;

  // Question Comments
  @PostMapping(
      value = "api/questions/{questionId}/comments",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> addQuestionComment(
      @PathVariable(value = "questionId") Long questionId, @RequestBody Comment comment) {
    Question question =
        questionService
            .getQuestionById(questionId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Question not found for id " + questionId));
    comment.setQuestion(question);
    comment = commentService.addComment(comment);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(comment.getId())
            .toUri();
    return ResponseEntity.created(uri).body(comment);
  }

  @GetMapping(
      value = "api/questions/{questionId}/comments",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Comment>> getAllQuestionComment(
      @PathVariable(value = "questionId") Long questionId) {
    return commentService
        .getCommentOfQuestion(questionId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("No comments found for question " + questionId));
  }

  // Answer Comments

  @PostMapping(
      value = "api/answers/{answerId}/comments",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> addAnswerComment(
      @PathVariable(value = "answerId") Long answerId, @RequestBody Comment comment) {
    Answer answer =
        answerService
            .getAnswerById(answerId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Answer not found for id " + answerId));
    comment.setAnswer(answer);
    comment = commentService.addComment(comment);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(comment.getId())
            .toUri();
    return ResponseEntity.created(uri).body(comment);
  }

  @GetMapping(
      value = "api/answers/{answerId}/comments",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Comment>> getAllAnswerComment(
      @PathVariable(value = "answerId") Long answerId) {
    return commentService
        .getCommentOfAnswer(answerId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResourceNotFoundException("No comments found for answer " + answerId));
  }

  // common comments

  @PutMapping(
      value = {
        "api/answers/{answerId}/comments/{commentId}",
        "api/questions/{questionId}/comments/{commentId}"
      },
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> updateComment(
      @RequestBody Comment comment,
      @PathVariable(value = "questionId", required = false) Long questionId,
      @PathVariable(value = "answerId", required = false) Long answerId,
      @PathVariable(value = "commentId") Long commentId) {
    comment.setId(commentId);
    return Optional.of(commentService.updateComment(comment))
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("No comment found"));
  }

  @DeleteMapping(
      value = {
        "api/answers/{answerId}/comments/{commentId}",
        "api/questions/{questionId}/comments/{commentId}"
      })
  public ResponseEntity<Void> deleteComment(
      @PathVariable(value = "questionId", required = false) Long questionId,
      @PathVariable(value = "answerId", required = false) Long answerId,
      @PathVariable(value = "commentId") Long commentId) {
    commentService.deleteComment(commentId);
    return ResponseEntity.noContent().build();
  }
}
