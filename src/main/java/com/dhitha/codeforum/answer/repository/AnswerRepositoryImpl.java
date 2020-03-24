package com.dhitha.codeforum.answer.repository;

import com.dhitha.codeforum.answer.model.Answer;
import com.dhitha.codeforum.common.component.RepositoryUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {

  @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<Answer> findByQuestionId(Long questionId) {
    return namedParameterJdbcTemplate.query(
        "select * from answer where question_id= :questionId",
        new MapSqlParameterSource("questionId", questionId),
        new AnswerRowMapper());
  }

  @Override
  public Answer findById(Long answerId, Long questionId) throws EmptyResultDataAccessException {
    return namedParameterJdbcTemplate.queryForObject(
        "select * from answer where question_id= :questionId and id= :answerId",
        new MapSqlParameterSource("questionId", questionId).addValue("answerId", answerId),
        new AnswerRowMapper());
  }

  @Override
  public Answer save(Answer answer) {
    KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(
        "insert into answer (id, question_id, created_at, created_by, text) values "
            + "((select isnull(max(id),0) from answer where question_id=:question_id) + 1, :question_id, :created_at, :created_by, :text);",
        new MapSqlParameterSource("created_by", answer.getCreatedBy())
            .addValue("question_id", answer.getQuestionId())
            .addValue("created_at", Timestamp.valueOf(answer.getCreatedAt()))
            .addValue(
                "text", new SqlLobValue(answer.getText(), new DefaultLobHandler()), Types.CLOB),
        generatedKeyHolder,
        new String[] {"id"});

    answer.setId(generatedKeyHolder.getKey().longValue());
    return answer;
  }

  @Override
  public Answer update(Answer answer) throws EmptyResultDataAccessException {
    Answer existingAnswer = findById(answer.getId(), answer.getQuestionId());
    String[] ignoredProperties = RepositoryUtil.getNullPropertyNames(answer);
    BeanUtils.copyProperties(answer, existingAnswer, ignoredProperties);
    String updateQuery = createUpdateStatement();
    namedParameterJdbcTemplate.update(updateQuery, getUpdateMapSqlParameterSource(existingAnswer));
    return existingAnswer;
  }

  private String createUpdateStatement() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
        .append("update answer set")
        .append(" text=:text,")
        .append(" up_vote=:up_vote,")
        .append(" updated_by=:updated_by,")
        .append(" updated_at=:updated_at")
        .append(" where id=:id ")
        .append(" and question_id=:question_id");

    return stringBuilder.toString();
  }

  private MapSqlParameterSource getUpdateMapSqlParameterSource(Answer answer) {
    return new MapSqlParameterSource("id", answer.getId())
        .addValue("text", new SqlLobValue(answer.getText(), new DefaultLobHandler()), Types.CLOB)
        .addValue("up_vote", answer.getUpVote())
        .addValue("updated_by", answer.getUpdatedBy())
        .addValue("updated_at", answer.getUpdatedAt())
        .addValue("question_id", answer.getQuestionId());
  }

  @Override
  public boolean deleteById(Long answerId, Long questionId) {
    return namedParameterJdbcTemplate.update(
            "delete from answer where id=:answerId and question_id=:questionId",
            new MapSqlParameterSource("questionId", questionId).addValue("answerId", answerId))
        > 0;
  }

  @Override
  public boolean deleteAllOfQuestion(Long questionId) {
    return namedParameterJdbcTemplate.update(
            "delete from answer where question_id=:questionId",
            new MapSqlParameterSource("questionId", questionId))
        > 0;
  }

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
      Timestamp updatedAt = rs.getTimestamp("updated_at");
      answer.setUpdatedAt(updatedAt==null?null:updatedAt.toLocalDateTime());
      answer.setUpdatedBy(rs.getObject("updated_by",Long.class));
      return answer;
    }
  }
}
