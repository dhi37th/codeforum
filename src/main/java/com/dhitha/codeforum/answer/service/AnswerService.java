package com.dhitha.codeforum.answer.service;

import com.dhitha.codeforum.answer.model.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AnswerService {

  /**
   * Add a new Answer
   *
   * @param answer Answer to add
   * @return Answer
   */
  Answer addAnswer(Answer answer);

  /**
   * Update an existing Answer
   *
   * @param answer Answer to update
   * @return Answer
   */
  Answer updateAnswer(Answer answer);

  /**
   * Delete a Answer
   *
   * @param answerId id of Answer to delete
   */
  void deleteAnswer(Long answerId);

  /**
   * Get all Answer tagged to a user
   *
   * @param questionId id of user
   * @return ArrayList
   */
  Optional<List<Answer>> getAllAnswersOfQuestion(Long questionId);

  /**
   * Get a Answer by id & question id
   *
   * @param questionId question id
   * @param answerId id
   * @return Optional
   */
  Optional<Answer> getAnswerById(Long questionId, Long answerId);

  /**
   * Get a answer by id
   *
   * @param answerId
   * @return
   */
  Optional<Answer> getAnswerById(Long answerId);

  /**
   * Return list of all Answer of a user based on pagination
   *
   * @param pageNumber pageNumber
   * @param limit limit
   * @param questionId questionId
   * @return Page
   */
  Page<Answer> getAllAnswersOfQuestion(int pageNumber, int limit, Long questionId);
}
