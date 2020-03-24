package com.dhitha.codeforum.comment.repository;

import com.dhitha.codeforum.comment.model.Comment;
import java.util.List;

public interface QuestionCommentRepository {

  Comment findById(Long id, Long questionId);

  List<Comment> findAll(Long questionId);

  Comment save(Comment comment);

  Comment update(Comment comment);

  boolean delete(Long id, Long questionId);

  boolean deleteAll(Long questionId);
}
