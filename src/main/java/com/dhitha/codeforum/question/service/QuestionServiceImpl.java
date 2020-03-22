package com.dhitha.codeforum.question.service;

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

  @Autowired SessionInfo sessionInfo;

  @Override
  public Question addQuestion(Question question) {
    question.setCreatedBy(sessionInfo.getSessionUser().getId());
    question.setCreatedAt(LocalDateTime.now());
    return questionRepository.save(question);
  }

  @Override
  public Question updateQuestion(Question question) {
    return null;
  }

  @Override
  public boolean deleteQuestion(Long questionId) {
    //delete all question comments
    //delete all answer comments
    //delete all answers
    return questionRepository.delete(questionId);
  }

  @Override
  public Optional<List<Question>> getAllCreatedBy(Long userId) {
    List<Question> questions = questionRepository.findAllCreatedBy(userId);
    if(questions.isEmpty()) return Optional.empty();
    return Optional.of(questions);
  }

  @Override
  public Optional<List<Question>> getAllQuestions() {
    List<Question> questions = questionRepository.findAll();
    if(questions.isEmpty()) return Optional.empty();
    return Optional.of(questions);
  }

  @Override
  public Optional<Question> getQuestionById(Long questionId) {
    try{
     Question question = questionRepository.findById(questionId);
     return Optional.ofNullable(question);
    }catch (IncorrectResultSizeDataAccessException e){
      log.error("Error in fetching question with id "+questionId,e);
      return Optional.empty();
    }
  }

  @Override
  public List<Question> getAll(int limit, int offset) {
    return questionRepository.findAll(limit, offset);
  }

  @Override
  public List<Question> getAllCreatedBy(int limit, int offset, Long userId) {
    return questionRepository.findAllCreatedBy(limit, offset, userId);
  }
}
