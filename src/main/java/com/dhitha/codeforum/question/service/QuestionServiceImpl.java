package com.dhitha.codeforum.question.service;

import com.dhitha.codeforum.answer.service.AnswerService;
import com.dhitha.codeforum.comment.service.AnswerCommentService;
import com.dhitha.codeforum.comment.service.QuestionCommentService;
import com.dhitha.codeforum.common.component.SessionInfo;
import com.dhitha.codeforum.question.model.Question;
import com.dhitha.codeforum.question.repository.QuestionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuestionServiceImpl implements QuestionService {

  @Autowired QuestionRepository questionRepository;

  @Autowired AnswerService answerService;

  @Autowired QuestionCommentService questionCommentService;

  @Autowired AnswerCommentService answerCommentService;

  @Autowired SessionInfo sessionInfo;

  @Override
  public Question addQuestion(Question question) {
    question.setCreatedBy(sessionInfo.getSessionUser().getId());
    question.setCreatedAt(LocalDateTime.now());
    return questionRepository.save(question);
  }

  @Override
  public Optional<Question> updateQuestion(Question question) {
    try {
      question.setUpdatedBy(sessionInfo.getSessionUser().getId());
      question.setUpdatedAt(LocalDateTime.now());
      question = questionRepository.update(question);
      return Optional.of(question);
    } catch (IncorrectResultSizeDataAccessException e) {
      log.error("Error in fetching question with id " + question.getId(), e);
      return Optional.empty();
    }
  }

  @Override
  public boolean deleteQuestion(Long questionId) {
    // delete all question comments
    questionCommentService.deleteAllComment(questionId);
    log.info("question comment deleted");
    // delete all answer comments
    answerService
        .getAllAnswersOfQuestion(questionId)
        .ifPresent(
            answers -> {
              answers.forEach(
                  answer ->
                      answerCommentService.deleteAllComment(
                          answer.getQuestionId(), answer.getId()));
            });
    log.info("answer comment deleted");
    // delete all answers
    answerService.deleteAllAnswer(questionId);
    return questionRepository.delete(questionId);
  }

  @Override
  public Optional<List<Question>> getAllCreatedBy(Long userId) {
    List<Question> questions = questionRepository.findAllCreatedBy(userId);
    if (questions.isEmpty()) return Optional.empty();
    return Optional.of(questions);
  }

  @Override
  public Optional<List<Question>> getAllQuestions() {
    List<Question> questions = questionRepository.findAll();
    if (questions.isEmpty()) return Optional.empty();
    return Optional.of(questions);
  }

  @Override
  public Optional<Question> getQuestionById(Long questionId) {
    try {
      Question question = questionRepository.findById(questionId);
      log.info("getQuestionById question "+question);
      return Optional.of(question);
    } catch (IncorrectResultSizeDataAccessException e) {
      log.error("Error in fetching question with id " + questionId, e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<Question>> getAllQuestions(int limit, int offset) {
    if (limit == 0 && offset == 0) return getAllQuestions();
    List<Question> questions = questionRepository.findAll(limit, offset);
    if (questions.isEmpty()) return Optional.empty();
    return Optional.of(questions);
  }

  @Override
  public Optional<List<Question>> getAllCreatedBy(int limit, int offset, Long userId) {
    if (limit == 0 && offset == 0) return getAllCreatedBy(userId);

    List<Question> questions = questionRepository.findAllCreatedBy(limit, offset, userId);
    if (questions.isEmpty()) return Optional.empty();
    return Optional.of(questions);
  }
}
