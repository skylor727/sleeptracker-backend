package com.sleeptracker.sleeptracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class SessionService {

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


    public boolean isSessionValid(String sessionTokenFromCookie) {
        String sessionToken = getSessionTokenFromDatabase(sessionTokenFromCookie);

        if (sessionToken != null) {
            return sessionTokenFromCookie.equals(sessionToken);
        }
        return false;
    }
}
