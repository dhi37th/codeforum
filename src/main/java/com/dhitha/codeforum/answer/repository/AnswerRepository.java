package com.dhitha.codeforum.answer.repository;

import com.dhitha.codeforum.answer.model.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

  /**
   * Find the answer per question id
   * @param questionId question id
   * @return List
   */
  Optional<List<Answer>> findByQuestionId(Long questionId);

  /**
   * Find the answer per question id
   *
   * @param questionId question id
   * @param pageable per page data
   * @return Page
   */
  Page<Answer> findByQuestionId(Long questionId, Pageable pageable);

  /**
   * Find answer by answer & question id
   * @param answerId answer id
   * @param questionId question id
   * @return
   */
  Optional<Answer> findByIdAndQuestionId(Long answerId, Long questionId);
}
