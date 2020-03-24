package com.dhitha.codeforum.comment.repository;

import com.dhitha.codeforum.comment.model.Comment;
import com.dhitha.codeforum.common.component.RepositoryUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionCommentRepositoryImpl implements QuestionCommentRepository {

  @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public Comment findById(Long id, Long questionId) {
    return namedParameterJdbcTemplate.queryForObject(
        "select * from question_comment where id=:id and question_id=:question_id",
        new MapSqlParameterSource("id", id).addValue("question_id", questionId),
        new QuestionCommentRowMapper());
  }

  @Override
  public List<Comment> findAll(Long questionId) {
    return namedParameterJdbcTemplate.query(
        "select * from question_comment where question_id=:question_id",
        new MapSqlParameterSource("question_id", questionId),
        new QuestionCommentRowMapper());
  }

  @Override
  public Comment save(Comment comment) {
    KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(
        "insert into question_comment (id,question_id, created_at,created_by, text) values "
            + "((select isnull(max(id),0) from question_comment  where question_id=:question_id) + 1,:question_id, :created_at, :created_by, :text);",
        new MapSqlParameterSource("created_by", comment.getCreatedBy())
            .addValue("question_id", comment.getQuestionId())
            .addValue("created_at", Timestamp.valueOf(comment.getCreatedAt()))
            .addValue(
                "text", new SqlLobValue(comment.getText(), new DefaultLobHandler()), Types.CLOB),
        generatedKeyHolder,
        new String[] {"id"});

    comment.setId(generatedKeyHolder.getKey().longValue());
    return comment;
  }

  @Override
  public Comment update(Comment comment) {
    Comment existingComment = findById(comment.getId(), comment.getQuestionId());
    String[] ignoredProperties = RepositoryUtil.getNullPropertyNames(comment);
    BeanUtils.copyProperties(comment, existingComment, ignoredProperties);
    String updateQuery = createUpdateStatement();
    namedParameterJdbcTemplate.update(updateQuery, getUpdateMapSqlParameterSource(existingComment));
    return existingComment;
  }

  private String createUpdateStatement() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
        .append("update question_comment set ")
        .append(" text=:text,")
        .append(" up_vote=:up_vote,")
        .append(" updated_by=:updated_by,")
        .append(" updated_at=:updated_at")
        .append(" where id=:id and question_id=:question_id");

    return stringBuilder.toString();
  }

  private MapSqlParameterSource getUpdateMapSqlParameterSource(Comment comment) {
    return new MapSqlParameterSource("id", comment.getId())
        .addValue("text", new SqlLobValue(comment.getText(), new DefaultLobHandler()), Types.CLOB)
        .addValue("question_id", comment.getQuestionId())
        .addValue("up_vote", comment.getUpVote())
        .addValue("updated_by", comment.getUpdatedBy())
        .addValue("updated_at", comment.getUpdatedAt());
  }

  @Override
  public boolean delete(Long id, Long questionId) {
    return namedParameterJdbcTemplate.update(
            "delete from question_comment where id=:id and question_id=:question_id",
            new MapSqlParameterSource("id", id).addValue("question_id", questionId))
        > 0;
  }

  @Override
  public boolean deleteAll(Long questionId) {
    return namedParameterJdbcTemplate.update(
            "delete from question_comment where question_id=:question_id",
            new MapSqlParameterSource("question_id", questionId))
        > 0;
  }

  private class QuestionCommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
      Comment comment = new Comment();
      comment.setId(rs.getLong("id"));
      comment.setText(rs.getString("text"));
      comment.setQuestionId(rs.getLong("question_id"));
      comment.setUpVote(rs.getLong("up_vote"));
      comment.setCreatedBy(rs.getLong("created_by"));
      comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
      return comment;
    }
  }
}
