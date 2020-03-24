package com.dhitha.codeforum.comment.service;

import com.dhitha.codeforum.comment.model.Comment;
import java.util.List;
import java.util.Optional;

public interface AnswerCommentService {

  /**
   * Get all comments of a question
   * @param questionId question id
   * @param answerId answer id
   * @return Optional
   */
  Optional<List<Comment>> getAllComments(Long questionId, Long answerId);

  /**
   * Get individual comment
   * @param commentId id
   * @param questionId question id
   * @param answerId answer id
   * @return
   */
  Optional<Comment> getComment(Long commentId, Long questionId, Long answerId);

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
   * @param answerId answer id
   */
  void deleteComment(Long commentId, Long questionId, Long answerId);

  /**
   * Delete all comments
   * @param questionId question id
   * @param answerId answer id
   */
  void deleteAllComment(Long questionId, Long answerId);

}
