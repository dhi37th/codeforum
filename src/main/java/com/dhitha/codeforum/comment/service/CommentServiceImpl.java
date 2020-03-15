package com.dhitha.codeforum.comment.service;

import com.dhitha.codeforum.comment.model.Comment;
import com.dhitha.codeforum.comment.repository.CommentRepository;
import com.dhitha.codeforum.common.component.RepositoryUtility;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired CommentRepository commentRepository;

  @Override
  public Optional<List<Comment>> getCommentOfQuestion(Long questionId) {
    return commentRepository.findByQuestionId(questionId);
  }

  @Override
  public Optional<List<Comment>> getCommentOfAnswer(Long answerId) {
    return commentRepository.findByAnswerId(answerId);
  }

  @Override
  public Comment addComment(Comment comment) {
    return commentRepository.saveAndFlush(comment);
  }

  @Override
  public Comment updateComment(Comment comment) {
    return commentRepository
        .findById(comment.getId())
        .map(
            existingComment -> {
              String[] ignoreProperties = RepositoryUtility.getNullPropertyNames(comment);
              BeanUtils.copyProperties(comment, existingComment, ignoreProperties);
              return commentRepository.saveAndFlush(existingComment);
            })
        .orElse(null);
  }

  @Override
  public void deleteComment(Long commentId) {
    commentRepository.deleteById(commentId);
  }
}
