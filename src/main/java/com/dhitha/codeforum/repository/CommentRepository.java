package com.dhitha.codeforum.repository;

import com.dhitha.codeforum.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  /**
   * Find the comments per question id
   *
   * @param questionId question id
   * @param pageable per page data
   * @return Page
   */
  Page<Comment> findByQuestionId(Long questionId, Pageable pageable);
}
