package com.sleeptracker.sleeptracker.services;

import com.sleeptracker.sleeptracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SessionService implements AuthenticationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String getSessionTokenFromDatabase(String sessionToken) {
        String sql = "SELECT \"sessionToken\" FROM public.\"Session\" WHERE \"sessionToken\" = ?";
        RowMapper<String> rowMapper = new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString("sessionToken");
            }
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, sessionToken);
    }

    @Override
    public boolean isSessionValid(String sessionTokenFromCookie) {
        String sessionToken = getSessionTokenFromDatabase(sessionTokenFromCookie);
        return sessionToken != null && sessionTokenFromCookie.equals(sessionToken);
    }

    @Override
    public User getUserBySessionToken(String sessionToken) {
        if (sessionToken != null) {
            String sql = "SELECT u.\"id\", u.\"name\", u.\"email\" " +
                    "FROM public.\"User\" u " +
                    "JOIN public.\"Session\" s ON u.\"id\" = s.\"userId\" " +
                    "WHERE s.\"sessionToken\" = ?";
            RowMapper<User> rowMapper = (resultSet, rowNum) -> new User(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email")
            );
            return jdbcTemplate.queryForObject(sql, rowMapper, sessionToken);
        }
        return null;
    }

    @Override
    public String getUserIdBySessionToken(String sessionToken) {
        if (sessionToken != null) {
            String sql = "SELECT u.\"id\" " +
                    "FROM public.\"User\" u " +
                    "JOIN public.\"Session\" s ON u.\"id\" = s.\"userId\" " +
                    "WHERE s.\"sessionToken\" = ?";
            RowMapper<String> rowMapper = (resultSet, rowNum) -> resultSet.getString("id");
            return jdbcTemplate.queryForObject(sql, rowMapper, sessionToken);
        }
        return null;
    }
}
