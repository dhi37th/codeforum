package com.dhitha.codeforum.comment.controller;

import com.dhitha.codeforum.comment.model.Comment;
import com.dhitha.codeforum.comment.service.QuestionCommentService;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonView;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("api/questions/{questionId}/comments/")
public class QuestionCommentController {
  @Autowired
  QuestionCommentService questionCommentService;

  @JsonView(Comment.QuestionView.class)
  @GetMapping(value = "{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> getCommentByIdOfQuestion(
      @PathVariable("questionId") long questionId, @PathVariable("commentId") long commentId) {

    return questionCommentService
        .getComment(commentId, questionId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Comment for id " + commentId + " & question id " + questionId + " not found"));
  }

  @JsonView(Comment.QuestionView.class)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Comment>> getAllCommentOfQuestion(
      @PathVariable("questionId") long questionId) {
    return questionCommentService
        .getAllComments(questionId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Comments for question id " + questionId + " not found"));
  }


  @JsonView(Comment.QuestionView.class)
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> saveCommentForQuestion(
      @PathVariable("questionId") long questionId, @Valid @RequestBody Comment comment) {
    comment.setQuestionId(questionId);
    comment = questionCommentService.addComment(comment);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(comment.getId())
            .toUri();
    return ResponseEntity.created(uri).body(comment);
  }

  @DeleteMapping(value = "{commentId}")
  public ResponseEntity<Void> deleteQuestionComment(
      @PathVariable("questionId") Long questionId, @PathVariable("commentId") Long commentId) {
    questionCommentService.deleteComment(commentId, questionId);
    return ResponseEntity.noContent().build();
  }


  @JsonView(Comment.QuestionView.class)
  @PutMapping(
      value = "{commentId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> updateCommentForQuestion(
      @PathVariable("commentId") Long commentId,
      @PathVariable("questionId") Long questionId,
      @Valid @RequestBody Comment comment) {
    comment.setId(commentId);
    comment.setQuestionId(questionId);
    return questionCommentService
        .updateComment(comment)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Comment not found for id: " + commentId));
  }
}
