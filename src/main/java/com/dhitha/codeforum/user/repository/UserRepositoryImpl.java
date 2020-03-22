package com.dhitha.codeforum.user.repository;

import com.dhitha.codeforum.user.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

  @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public User findByLoginId(String loginId) {
    User user =  namedParameterJdbcTemplate.queryForObject(
        "select * from user where login_id= :loginId",
        new MapSqlParameterSource("loginId", loginId),
        new UserRowMapper());
    return user;
  }

  public static class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setId(rs.getLong("id"));
      user.setIsAdmin(rs.getBoolean("is_admin"));
      user.setLoginId(rs.getString("login_id"));
      user.setPassword(rs.getString("password"));
      user.setName(rs.getString("name"));
      return user;
    }
  }
}
