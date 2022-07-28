package com.mp_music.mp_music.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mp_music.mp_music.model.PlatformModel;
import com.mp_music.mp_music.repository.IPlatformRepository;

@Repository
public class PlatformRepository implements IPlatformRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int findPlatformId(String platformName) {
        var platIdQuery = "SELECT * FROM platforms WHERE name = ?";
        List<PlatformModel> platIdList = jdbc.query(platIdQuery,
                new BeanPropertyRowMapper<PlatformModel>(PlatformModel.class), platformName);
        return platIdList.get(0).getId();
    }
}
