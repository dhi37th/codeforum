package com.dhitha.codeforum.comment.controller;

import com.dhitha.codeforum.comment.model.Comment;
import com.dhitha.codeforum.comment.service.AnswerCommentService;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/questions/{questionId}/answers/{answerId}/comments")
public class AnswerCommentController {
  @Autowired AnswerCommentService answerCommentService;

  @GetMapping(value = "/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> getCommentByIdOfAnswer(
      @PathVariable("questionId") long questionId,
      @PathVariable("answerId") long answerId,
      @PathVariable("commentId") long commentId) {

    return answerCommentService
        .getComment(commentId, questionId, answerId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Comment for id " + commentId + " & answer id " + answerId + " not found"));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Comment>> getAllCommentOfAnswer(
      @PathVariable("questionId") long questionId, @PathVariable("answerId") long answerId) {
    return answerCommentService
        .getAllComments(questionId, answerId)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () ->
                new ResourceNotFoundException("Comments for answer id " + answerId + " not found"));
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> saveCommentForAnswer(
      @PathVariable("questionId") long questionId,
      @PathVariable("answerId") long answerId,
      @Valid @RequestBody Comment comment) {
    comment.setAnswerId(answerId);
    comment.setQuestionId(questionId);
    comment = answerCommentService.addComment(comment);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(comment.getId())
            .toUri();
    return ResponseEntity.created(uri).body(comment);
  }

  @DeleteMapping(value = "/{commentId}")
  public ResponseEntity<Void> deleteAnswerComment(
      @PathVariable("questionId") long questionId,
      @PathVariable("answerId") Long answerId,
      @PathVariable("commentId") Long commentId) {
    answerCommentService.deleteComment(commentId, questionId, answerId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(
      value = "/{commentId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Comment> updateCommentForAnswer(
      @PathVariable("questionId") long questionId,
      @PathVariable("commentId") Long commentId,
      @PathVariable("answerId") Long answerId,
      @Valid @RequestBody Comment comment) {
    comment.setId(commentId);
    comment.setAnswerId(answerId);
    comment.setQuestionId(questionId);
    return answerCommentService
        .updateComment(comment)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResourceNotFoundException("Comment not found for id: " + commentId));
  }
}
