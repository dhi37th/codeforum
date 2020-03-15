package com.dhitha.codeforum.answer.service;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.answer.repository.AnswerRepository;
import com.dhitha.codeforum.common.component.RepositoryUtility;
import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

  @Autowired AnswerRepository answerRepository;

  @Override
  public  Answer addAnswer(Answer answer) {
    return answerRepository.saveAndFlush(answer);
  }

  @Override
  public Answer updateAnswer(Answer answer) {
    return answerRepository.findById(answer.getId()).map(existingAnswer -> {
      String[] ignoreProperties = RepositoryUtility.getNullPropertyNames(answer);
      BeanUtils.copyProperties(answer, existingAnswer, ignoreProperties);
      return answerRepository.saveAndFlush(existingAnswer);
    }).orElse(null);
  }

  @Override
  public void deleteAnswer(Long answerId) {
    answerRepository.deleteById(answerId);
  }

  @Override
  public Optional<List<Answer>> getAllAnswersOfQuestion(Long questionId) {
    return answerRepository.findByQuestionId(questionId);
  }

  @Override
  public Optional<Answer> getAnswerById(Long questionId, Long answerId) {
    return answerRepository.findByIdAndQuestionId(answerId, questionId);
  }

  @Override
  public Optional<Answer> getAnswerById(Long answerId) {
    return answerRepository.findById(answerId);
  }

  @Override
  public Page<Answer> getAllAnswersOfQuestion(int pageNumber, int limit, Long questionId) {
    Pageable pageable = PageRequest.of(pageNumber, limit);
    return answerRepository.findByQuestionId(questionId, pageable);
  }
}
