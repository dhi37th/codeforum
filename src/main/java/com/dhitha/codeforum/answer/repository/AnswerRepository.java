package com.dhitha.codeforum.answer.repository;

import com.dhitha.codeforum.answer.model.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
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
  Answer findById(Long answerId, Long questionId) throws EmptyResultDataAccessException;

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
  Answer update(Answer answer) throws EmptyResultDataAccessException;

  /**
   * Delete an answer
   * @param answerId answer id
   * @param questionId question id
   */
  boolean deleteById(Long answerId, Long questionId);

  /**
   * Delete all answer of a question
   * @param questionId question id
   */
  boolean deleteAllOfQuestion(Long questionId);
}
