package com.dhitha.codeforum.question.repository;

import com.dhitha.codeforum.question.model.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  List<Question> findAllByUserId(Long userId);
}
