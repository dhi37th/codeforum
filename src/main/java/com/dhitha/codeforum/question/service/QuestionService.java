package com.dhitha.codeforum.question.service;

import com.dhitha.codeforum.question.model.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionService {

  /**
   * Add a new question
   *
   * @param question question to add
   * @return Question
   */
  Question addQuestion(Question question);

  /**
   * Update an existing question
   *
   * @param question question to update
   * @return Question
   */
  Optional<Question> updateQuestion(Question question);

  /**
   * Delete a question
   *
   * @param questionId id of question to delete
   */
  boolean deleteQuestion(Long questionId);

  /**
   * Get all question tagged to a user
   *
   * @param userId id of user
   * @return ArrayList
   */
  Optional<List<Question>> getAllCreatedBy(Long userId);

  /**
   * Get all question in forum
   *
   * @return
   */
  Optional<List<Question>> getAllQuestions();

  /**
   * Get a question by id
   *
   * @param questionId id
   * @return Optional
   */
  Optional<Question> getQuestionById(Long questionId);

  /**
   * Find all questions with page limits
   * @param limit no of records to return
   * @param offset offset from where to return
   * @return List (may be empty list if no questions found)
   */
  Optional<List<Question>> getAllQuestions(int limit, int offset);

  /**
   * Find all created by with page limits
   * @param limit no of record to return
   * @param offset offset from where to return
   * @param userId user id
   * @return List
   */
  Optional<List<Question>> getAllCreatedBy(int limit, int offset,Long userId);

}
