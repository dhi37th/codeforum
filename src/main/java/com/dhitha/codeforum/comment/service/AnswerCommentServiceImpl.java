package com.dhitha.codeforum.comment.service;

import com.dhitha.codeforum.comment.model.Comment;
import com.dhitha.codeforum.comment.repository.AnswerCommentRepository;
import com.dhitha.codeforum.common.component.SessionUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnswerCommentServiceImpl implements AnswerCommentService {

  @Autowired AnswerCommentRepository answerCommentRepository;
  @Autowired
  SessionUtil sessionUtil;

  @Override
  public Optional<List<Comment>> getAllComments(Long questionId, Long answerId) {
    List<Comment> comments = answerCommentRepository.findAll(questionId, answerId);
    if (comments.isEmpty()) return Optional.empty();
    return Optional.of(comments);
  }

  @Override
  public Optional<Comment> getComment(Long id, Long questionId, Long answerId) {
    try {
      Comment comment = answerCommentRepository.findById(id, questionId, answerId);
      return Optional.ofNullable(comment);
    } catch (IncorrectResultSizeDataAccessException e) {
      log.error("Error in fetching comment with id : {} and answer id {}" + id, answerId, e);
      return Optional.empty();
    }
  }

  @Override
  public Comment addComment(Comment comment) {
    comment.setCreatedBy(sessionUtil.getSessionUser().getId());
    comment.setCreatedAt(LocalDateTime.now());
    return answerCommentRepository.save(comment);
  }

  @Override
  public Optional<Comment> updateComment(Comment comment) {
    try {
      comment.setUpdatedBy(sessionUtil.getSessionUser().getId());
      comment.setUpdatedAt(LocalDateTime.now());
      answerCommentRepository.update(comment);
      return Optional.of(comment);
    } catch (IncorrectResultSizeDataAccessException e) {
      log.error("Error in fetching comment with id " + comment.getId(), e);
      return Optional.empty();
    }
  }

  @Override
  public void deleteComment(Long commentId, Long questionId, Long answerId) {
    answerCommentRepository.delete(commentId, questionId, answerId);
  }

  @Override
  public void deleteAllComment(Long questionId, Long answerId) {
    answerCommentRepository.deleteAll(questionId, answerId);
  }
}
