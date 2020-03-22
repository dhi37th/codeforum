package com.dhitha.codeforum.answer.repository;

import com.dhitha.codeforum.answer.model.Answer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {

  @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<Answer> findByQuestionId(Long questionId) {
    return namedParameterJdbcTemplate.query(
        "select * from answer where questionid= :questionId",
        new MapSqlParameterSource("questionId", questionId),
        new AnswerRowMapper());
  }

  @Override
  public Answer findByIdAndQuestionId(Long answerId, Long questionId) {
    return namedParameterJdbcTemplate.queryForObject(
        "select * from answer where questionid= :questionId and id= :answerId",
        new MapSqlParameterSource("questionId", questionId).addValue("answerId", answerId),
        new AnswerRowMapper());
  }

  @Override
  public Answer save(Answer answer) {
    return null;
  }

  @Override
  public Answer update(Answer answer) {
    return null;
  }

  @Override
  public Answer findById(Long answerId) {
    return null;
  }

  @Override
  public void deleteById(Long answerId) {}

  @Override
  public void deleteAllOfQuestion(Long questionId) {}

  public class AnswerRowMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
      Answer answer = new Answer();
      answer.setId(rs.getLong("id"));
      answer.setQuestionId(rs.getLong("question_id"));
      answer.setText(rs.getString("text"));
      answer.setUpVote(rs.getLong("up_vote"));
      answer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
      answer.setCreatedBy(rs.getLong("created_by"));
      answer.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
      answer.setUpdatedBy(rs.getLong("updated_by"));
      return answer;
    }
  }
}
