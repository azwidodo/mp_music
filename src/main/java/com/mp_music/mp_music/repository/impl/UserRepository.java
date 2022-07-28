package com.mp_music.mp_music.repository.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mp_music.mp_music.model.UserModel;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    DataSource dataSource;

    public UserModel getUserModel(String username) {
        var query = "SELECT u.username, u.password, a.authority FROM users u "
                + "INNER JOIN authorities a ON u.username = a.username "
                + "WHERE u.enabled = 1 AND u.username = ?";

        return jdbc.query(query, new BeanPropertyRowMapper<UserModel>(UserModel.class), username).get(0);
    }
}
