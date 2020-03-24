package com.dhitha.codeforum.answer.service;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.answer.repository.AnswerRepository;
import com.dhitha.codeforum.comment.service.AnswerCommentService;
import com.dhitha.codeforum.common.component.SessionUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

  @Autowired AnswerRepository answerRepository;

  @Autowired AnswerCommentService answerCommentService;

  @Autowired
  SessionUtil sessionUtil;

  @Override
  public Answer addAnswer(Answer answer) {
    answer.setCreatedBy(sessionUtil.getSessionUser().getId());
    answer.setCreatedAt(LocalDateTime.now());
    return answerRepository.save(answer);
  }

  @Override
  public Optional<Answer> updateAnswer(Answer answer) {
    try {
      answer.setUpdatedBy(sessionUtil.getSessionUser().getId());
      answer.setUpdatedAt(LocalDateTime.now());
      return Optional.of(answerRepository.update(answer));
    } catch (EmptyResultDataAccessException e) {
      log.error(
          "Error in fetching answer with question id: {}, answer id: {}" + answer.getQuestionId(),
          answer.getId(),
          e);
      return Optional.empty();
    }
  }

  @Override
  public void deleteAnswer(Long answerId, Long questionId) {
    // delete all comments of answer
    answerCommentService.deleteAllComment(questionId, answerId);
    answerRepository.deleteById(answerId, questionId);
  }

  @Override
  public void deleteAllAnswer(Long questionId) {
    getAllAnswersOfQuestion(questionId)
        .ifPresent(
            answers -> {
              answers.forEach(
                  answer -> answerCommentService.deleteAllComment(questionId, answer.getId()));
            });
    answerRepository.deleteAllOfQuestion(questionId);
  }

  @Override
  public Optional<List<Answer>> getAllAnswersOfQuestion(Long questionId) {
    List<Answer> answerList = answerRepository.findByQuestionId(questionId);
    if (answerList.isEmpty()) return Optional.empty();
    return Optional.of(answerList);
  }

  @Override
  public Optional<Answer> getAnswerById(Long answerId, Long questionId) {
    try {
      Answer answer = answerRepository.findById(answerId, questionId);
      return Optional.ofNullable(answer);
    } catch (EmptyResultDataAccessException e) {
      log.error(
          "Error in fetching answer with question id: {}, answer id: {}" + questionId, answerId, e);
      return Optional.empty();
    }
  }
}
