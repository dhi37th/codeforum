package com.dhitha.codeforum.question.repository;

import com.dhitha.codeforum.question.model.Question;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

public interface QuestionRepository {

  /**
   * Find question by id
   *
   * @param questionId question id
   * @return Question
   */
  Question findById(Long questionId) throws EmptyResultDataAccessException;

  /**
   * Find all questions
   *
   * @return List (may be empty list if no questions found)
   */
  List<Question> findAll();

  /**
   * Find all questions created by user
   *
   * @param userId userId
   * @return List (may be empty list if no questions found)
   */
  List<Question> findAllCreatedBy(Long userId);

  /**
   * Find all questions with page limits
   * @param limit no of records to return
   * @param offset offset from where to return
   * @return List (may be empty list if no questions found)
   */
  List<Question> findAll(int limit, int offset);

  /**
   * Find all created by with page limits
   * @param limit no of record to return
   * @param offset offset from where to return
   * @param userId user id
   * @return List
   */
  List<Question> findAllCreatedBy(int limit, int offset,Long userId);

  /**
   * Save the question
   *
   * @param question question
   * @return Question
   */
  Question save(Question question);

  /**
   * Update the question
   *
   * @param question question
   * @return Question
   */
  Question update(Question question) throws EmptyResultDataAccessException;

  /**
   * Delete the question
   *
   * @param questionId question id
   */
  boolean delete(Long questionId) throws EmptyResultDataAccessException;
}
