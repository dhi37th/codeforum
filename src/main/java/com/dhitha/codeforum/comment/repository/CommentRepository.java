package com.dhitha.codeforum.comment.repository;

import com.dhitha.codeforum.comment.model.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  /**
   * Find the comments per question id
   *
   * @param questionId question id
   * @return List
   */
  Optional<List<Comment>> findByQuestionId(Long questionId);

  /**
   * Find the comments per answer id
   *
   * @param answerId answer id
   * @return List
   */
  Optional<List<Comment>> findByAnswerId(Long answerId);
}
