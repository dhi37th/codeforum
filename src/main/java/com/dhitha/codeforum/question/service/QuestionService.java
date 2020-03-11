package com.dhitha.codeforum.question.service;

import com.dhitha.codeforum.question.model.Question;
import java.util.List;
import org.springframework.data.domain.Page;

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
  Question updateQuestion(Question question);

  /**
   * Delete a question
   *
   * @param questionId id of question to delete
   */
  void deleteQuestion(Long questionId);

  /**
   * Get all question tagged to a user
   *
   * @param userId id of user
   * @return ArrayList
   */
  List<Question> getAllQuestionOfUser(Long userId);

  /**
   * Get a question by id
   *
   * @param questionId id
   * @return Question
   */
  Question getQuestionById(Long questionId);

  /**
   * Return list of all question based on pagination
   * @param pageNumber pageNumber
   * @param limit limit
   * @return Page
   */
  Page<Question> getAllQuestionOfUser(int pageNumber,int limit);
}
