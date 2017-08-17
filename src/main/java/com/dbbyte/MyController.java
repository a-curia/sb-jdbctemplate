package com.dbbyte;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class MyController {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        jdbcTemplate.update("INSERT INTO users (username, password) VALUES (?,?)", new Object[]{"username1", "password1"});
        return "insert completes!";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete() {
        jdbcTemplate.update("DELETE FROM users where id>?", 100);
        return "delete completes!";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update() {
        jdbcTemplate.update("UPDATE users SET username=?, password=? ", new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "update_username");
                ps.setString(2, "update_password");
            }
        });
        return "update completes!";
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query() {
        String name = jdbcTemplate.queryForObject("SELECT username FROM users where id = ?", String.class, 100);
        return "username " + name;
    }
}
