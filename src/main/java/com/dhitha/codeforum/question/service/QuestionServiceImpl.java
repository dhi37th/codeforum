package com.dhitha.codeforum.question.service;

import com.dhitha.codeforum.question.model.Question;
import com.dhitha.codeforum.question.repository.QuestionRepository;
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
public class QuestionServiceImpl implements QuestionService {

  @Autowired QuestionRepository questionRepository;

  @Override
  public Question addQuestion(Question question) {
    return questionRepository.saveAndFlush(question);
  }

  @Override
  public Question updateQuestion(Question question) {
    Optional<Question> existingQuestion = questionRepository.findById(question.getId());
    if (existingQuestion.isPresent()) {
      String[] ignoreProperties = getNullPropertyNames(question);
      BeanUtils.copyProperties(question, existingQuestion.get(), ignoreProperties);
      return questionRepository.saveAndFlush(existingQuestion.get());
    } else {
      return addQuestion(question);
    }
  }

  @Override
  public void deleteQuestion(Long questionId) {
    questionRepository.deleteById(questionId);
  }

  @Override
  public List<Question> getAllQuestionOfUser(Long userId) {
    return questionRepository.findAllByUserId(userId);
  }

  @Override
  public Question getQuestionById(Long questionId) {
    return questionRepository.findById(questionId).orElse(null);
  }

  @Override
  public Page<Question> getAllQuestionOfUser(int pageNumber, int limit) {
    Pageable pageable = PageRequest.of(pageNumber, limit);
    return questionRepository.findAll(pageable);
  }

  private String[] getNullPropertyNames(Question question) {
    BeanWrapper wrapper = new BeanWrapperImpl(question);

    return Stream.of(wrapper.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(name -> wrapper.getPropertyDescriptor(name) == null)
        .toArray(String[]::new);
  }
}
