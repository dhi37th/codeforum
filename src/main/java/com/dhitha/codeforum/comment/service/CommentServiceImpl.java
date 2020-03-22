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

  @Override
  public Optional<List<Comment>> getCommentOfQuestion(Long questionId) {
    return Optional.empty();
  }

  @Override
  public Optional<List<Comment>> getCommentOfAnswer(Long answerId) {
    return Optional.empty();
  }

  @Override
  public Comment addComment(Comment comment) {
    return null;
  }

  @Override
  public Comment updateComment(Comment comment) {
    return null;
  }

  @Override
  public void deleteComment(Long commentId) {

  }
}
