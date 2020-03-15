package com.dhitha.codeforum.comment.service;

import com.dhitha.codeforum.comment.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {

  /**
   * Get all comments of a question
   * @param questionId question id
   * @return Optional
   */
  Optional<List<Comment>> getCommentOfQuestion(Long questionId);

  /**
   * Get all comments of answer
   * @param answerId answer id
   * @return Optional
   */
  Optional<List<Comment>> getCommentOfAnswer(Long answerId);

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
  Comment updateComment(Comment comment);

  /**
   * Delete the question comment
   * @param commentId comment id
   */
  void deleteComment(Long commentId);

}
