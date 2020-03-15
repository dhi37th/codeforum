package com.dhitha.codeforum.question.service;

import com.dhitha.codeforum.common.component.RepositoryUtility;
import com.dhitha.codeforum.question.model.Question;
import com.dhitha.codeforum.question.repository.QuestionRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class QuestionServiceImpl implements QuestionService {

  @Autowired QuestionRepository questionRepository;

  @Override
  public Question addQuestion(Question question) {
    return questionRepository.saveAndFlush(question);
  }

  @Override
  public Question updateQuestion(Question question) {
    return questionRepository
        .findById(question.getId())
        .map(
            existingQuestion -> {
              String[] ignoreProperties = RepositoryUtility.getNullPropertyNames(question);
              BeanUtils.copyProperties(question, existingQuestion, ignoreProperties);
              log.info(existingQuestion);
              return questionRepository.saveAndFlush(existingQuestion);
            })
        .orElse(null);
  }

  @Override
  public void deleteQuestion(Long questionId) {
    questionRepository.deleteById(questionId);
  }

  @Override
  public Optional<List<Question>> getAllQuestionsOfUser(Long userId) {
    return questionRepository.findAllByCreatedBy(userId);
  }

  @Override
  public Optional<List<Question>> getAllQuestions() {
    return Optional.of(questionRepository.findAll());
  }

  @Override
  public Optional<Question> getQuestionById(Long questionId) {
    return questionRepository.findById(questionId);
  }

  @Override
  public Page<Question> getAllQuestionsOfUser(int pageNumber, int limit, Long userId) {
    Pageable pageable = PageRequest.of(pageNumber, limit);
    return questionRepository.findAllByCreatedBy(userId, pageable);
  }

  @Override
  public Page<Question> getAllQuestions(int pageNumber, int limit) {
    Pageable pageable = PageRequest.of(pageNumber, limit);
    return questionRepository.findAll(pageable);
  }
}
