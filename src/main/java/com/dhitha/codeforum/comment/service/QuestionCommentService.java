package com.dhitha.codeforum.comment.service;

import com.dhitha.codeforum.comment.model.Comment;
import java.util.List;
import java.util.Optional;

public interface QuestionCommentService {

  /**
   * Get all comments of a question
   * @param questionId question id
   * @return Optional
   */
  Optional<List<Comment>> getAllComments(Long questionId);

  /**
   * Get individual comment
   * @param id id
   * @param questionId question id
   * @return
   */
  Optional<Comment> getComment(Long id, Long questionId);

  /**
   * Add comment for a question
   * @param comment comment
   * @return Comment
   */
  Comment addComment(Comment comment);

  /**
   * Update comment
   * @param comment comment
   * @return Comment
   */
  Optional<Comment> updateComment(Comment comment);

  /**
   * Delete the comment
   * @param commentId comment id
   * @param questionId question id
   */
  void deleteComment(Long commentId, Long questionId);

  /**
   * Delete all comments
   * @param questionId question id
   */
  void deleteAllComment(Long questionId);

}
