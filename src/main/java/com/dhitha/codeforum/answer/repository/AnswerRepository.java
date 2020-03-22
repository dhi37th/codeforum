package com.dhitha.codeforum.answer.repository;

import com.dhitha.codeforum.answer.model.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public interface AnswerRepository {

  /**
   * Find the answer per question id
   * @param questionId question id
   * @return List
   */
  List<Answer> findByQuestionId(Long questionId);

  /**
   * Find answer by answer & question id
   * @param answerId answer id
   * @param questionId question id
   * @return Optional
   */
  Answer findByIdAndQuestionId(Long answerId, Long questionId);

  /**
   * Save an answer
   * @param answer answer
   * @return Answer
   */
  Answer save(Answer answer);

  /**
   * Update an answer
   * @param answer anser
   * @return Answer
   */
  Answer update(Answer answer);

  /**
   * Find an answer
   * @param answerId answerId
   * @return Optional
   */
  Answer findById(Long answerId);

  /**
   * Delete an answer
   * @param answerId answer id
   */
  void deleteById(Long answerId);

  /**
   * Delete all answer of a question
   * @param questionId question id
   */
  void deleteAllOfQuestion(Long questionId);
}
