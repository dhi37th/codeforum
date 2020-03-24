package com.dhitha.codeforum.comment.repository;

import com.dhitha.codeforum.comment.model.Comment;
import java.util.List;

public interface AnswerCommentRepository {

  Comment findById(Long id, Long questionId, Long answerId);

  List<Comment> findAll(Long questionId, Long answerId);

  Comment save(Comment comment);

  Comment update(Comment comment);

  boolean delete(Long id, Long questionId, Long answerId);

  boolean deleteAll(Long questionId, Long answerId);
}
