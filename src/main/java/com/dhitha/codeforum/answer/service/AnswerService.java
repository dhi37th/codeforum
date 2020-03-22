package com.dhitha.codeforum.answer.service;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
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
  Answer updateAnswer(Answer answer) throws ResourceNotFoundException;

  /**
   * Delete a Answer
   *
   * @param answerId id of Answer to delete
   */
  void deleteAnswer(Long answerId) throws ResourceNotFoundException;

  /**
   * Get all Answer tagged to a user
   *
   * @param questionId id of user
   * @return ArrayList
   */
  List<Answer> getAllAnswersOfQuestion(Long questionId) throws ResourceNotFoundException;

  /**
   * Get a Answer by id & question id
   *
   * @param questionId question id
   * @param answerId id
   * @return Optional
   */
  Answer getAnswerById(Long questionId, Long answerId) throws ResourceNotFoundException;

  /**
   * Get a answer by id
   *
   * @param answerId
   * @return
   */
  Answer getAnswerById(Long answerId) throws ResourceNotFoundException;

}
