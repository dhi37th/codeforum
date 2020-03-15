package com.dhitha.codeforum.question.repository;

import com.dhitha.codeforum.question.model.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  /**
   * Find all questions created by user
   * @param userId userId
   * @return Optional
   */
  Optional<List<Question>> findAllByCreatedBy(Long userId);

  /**
   * Find all questions created by user
   * @param userId userId
   * @param pageable pageable
   * @return Page
   */
  Page<Question> findAllByCreatedBy(Long userId,Pageable pageable);

}
