package com.dhitha.codeforum.answer.service;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.answer.repository.AnswerRepository;
import com.dhitha.codeforum.common.component.RepositoryUtility;
import com.dhitha.codeforum.common.model.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

  @Autowired AnswerRepository answerRepository;

  @Override
  public  Answer addAnswer(Answer answer) {
    return answerRepository.save(answer);
  }

  @Override
  public Answer updateAnswer(Answer answer) {
    return answerRepository.update(answer);
  }

  @Override
  public void deleteAnswer(Long answerId) {
    answerRepository.deleteById(answerId);
  }

  @Override
  public List<Answer> getAllAnswersOfQuestion(Long questionId) {
    List<Answer> answerList = answerRepository.findByQuestionId(questionId);
    if(answerList.isEmpty()) throw new ResourceNotFoundException("Answers not found for question id "+questionId);
    return answerList;
  }

  @Override
  public Answer getAnswerById(Long questionId, Long answerId) {
    return answerRepository.findByIdAndQuestionId(answerId, questionId);
  }

  @Override
  public Answer getAnswerById(Long answerId) {
    return answerRepository.findById(answerId);
  }

}
