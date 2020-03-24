package com.dhitha.codeforum.comment.service;

import com.dhitha.codeforum.comment.model.Comment;
import com.dhitha.codeforum.comment.repository.QuestionCommentRepository;
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
public class QuestionCommentServiceImpl implements QuestionCommentService {
  @Autowired QuestionCommentRepository questionCommentRepository;
  @Autowired
  SessionUtil sessionUtil;

  @Override
  public Optional<List<Comment>> getAllComments(Long answerId) {
    List<Comment> comments = questionCommentRepository.findAll(answerId);
    if (comments.isEmpty()) return Optional.empty();
    return Optional.of(comments);
  }

  @Override
  public Optional<Comment> getComment(Long id, Long answerId) {
    try {
      Comment comment = questionCommentRepository.findById(id, answerId);
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
    return questionCommentRepository.save(comment);
  }

  @Override
  public Optional<Comment> updateComment(Comment comment) {
    try {
      comment.setUpdatedBy(sessionUtil.getSessionUser().getId());
      comment.setUpdatedAt(LocalDateTime.now());
      questionCommentRepository.update(comment);
      return Optional.of(comment);
    } catch (IncorrectResultSizeDataAccessException e) {
      log.error("Error in fetching comment with id " + comment.getId(), e);
      return Optional.empty();
    }
  }

  @Override
  public void deleteComment(Long commentId, Long answerId) {
    questionCommentRepository.delete(commentId, answerId);
  }

  @Override
  public void deleteAllComment(Long questionId) {
    questionCommentRepository.deleteAll(questionId);
  }
}
