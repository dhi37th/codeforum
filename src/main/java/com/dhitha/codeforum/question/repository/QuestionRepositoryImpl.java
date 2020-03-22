package com.dhitha.codeforum.question.repository;

import com.dhitha.codeforum.question.model.Question;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class QuestionRepositoryImpl implements QuestionRepository {

  @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public Question findById(Long questionId) throws IncorrectResultSizeDataAccessException {
    return namedParameterJdbcTemplate.queryForObject(
        "select * from question where id= :questionId;",
        new MapSqlParameterSource("questionId", questionId),
        new QuestionMapper());
  }

  @Override
  public List<Question> findAll() {
    return namedParameterJdbcTemplate.query("select * from question;", new QuestionMapper());
  }

  @Override
  public List<Question> findAllCreatedBy(Long userId) {
    return namedParameterJdbcTemplate.query(
        "select * from question where created_by= :userId;",
        new MapSqlParameterSource("userId", userId),
        new QuestionMapper());
  }

  @Override
  public List<Question> findAll(int limit, int offset) {
    return namedParameterJdbcTemplate.query(
        "select * from question limit :limit offset :offset;",
        new MapSqlParameterSource("limit", limit).addValue("offset", offset),
        new QuestionMapper());
  }

  @Override
  public List<Question> findAllCreatedBy(int limit, int offset, Long userId) {
    return namedParameterJdbcTemplate.query(
        "select * from question where created_by= :userId limit :limit offset :offset;",
        new MapSqlParameterSource("limit", limit)
            .addValue("offset", offset)
            .addValue("userId", userId),
        new QuestionMapper());
  }

  @Override
  public Question save(Question question) {
    KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(
        "insert into question (created_at,created_by, heading, text, tag) values "
            + "(:created_at, :created_by, :heading, :text, :tag);",
        new MapSqlParameterSource("created_by", question.getCreatedBy())
            .addValue("created_at", Timestamp.valueOf(question.getCreatedAt()))
            .addValue("heading", question.getHeading())
            .addValue(
                "text", new SqlLobValue(question.getText(), new DefaultLobHandler()), Types.CLOB)
            .addValue("tag", question.getTag()),
        generatedKeyHolder,
        new String[] {"id"});

    question.setId(generatedKeyHolder.getKey().longValue());
    return question;
  }

  @Override
  public Question update(Question question) {
    return null;
  }

  @Override
  public boolean delete(Long questionId) {
    return namedParameterJdbcTemplate.update(
            "delete from question where id=:questionId",
            new MapSqlParameterSource("questionId", questionId))
        > 0;
  }

  public static class QuestionMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
      Question question = new Question();
      question.setId(rs.getLong("id"));
      question.setHeading(rs.getString("heading"));
      question.setText(rs.getString("text"));
      question.setUpVote(rs.getLong("up_vote"));
      question.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
      question.setCreatedBy(rs.getLong("created_by"));
      question.setTag(rs.getString("tag"));
      Timestamp updatedDate = rs.getTimestamp("updated_at");
      question.setUpdatedAt(updatedDate == null ? null : updatedDate.toLocalDateTime());
      question.setUpdatedBy(rs.getLong("updated_by"));
      return question;
    }
  }
}
