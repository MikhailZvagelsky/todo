package com.example.todo.dao;

import com.example.todo.models.TimestampedTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DbTodoDao implements TodoDao {

    private final JdbcTemplate jdbcTemplate;
    private final TimestampedTodoRowMapper rowMapper = new TimestampedTodoRowMapper();

    @Override
    public List<TimestampedTodo> getTodos() {
        String sql = """
                SELECT millis, text FROM todos;
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void save(TimestampedTodo timestampedTodo) {
        String sql = """
                INSERT INTO todos (millis, text) VALUES (?, ?);
                """;
        jdbcTemplate.update(sql, timestampedTodo.millis(), timestampedTodo.text());
    }

    private static class TimestampedTodoRowMapper implements RowMapper<TimestampedTodo> {

        @Override
        public TimestampedTodo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new TimestampedTodo(rs.getLong("millis"), rs.getString("text"));
        }
    }
}
