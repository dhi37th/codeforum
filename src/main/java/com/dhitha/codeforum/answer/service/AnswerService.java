package com.dhitha.codeforum.answer.service;

import com.dhitha.codeforum.answer.model.Answer;
import java.util.List;
import java.util.Optional;

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
  Optional<Answer> updateAnswer(Answer answer);

  /**
   * Delete a Answer
   *
   * @param answerId id of Answer to delete
   */
  void deleteAnswer(Long answerId, Long questionId);

  /**
   * Delete all answer for a question
   *
   * @param questionId question id
   */
  void deleteAllAnswer(Long questionId);

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
  Optional<Answer> getAnswerById(Long answerId, Long questionId);
}
