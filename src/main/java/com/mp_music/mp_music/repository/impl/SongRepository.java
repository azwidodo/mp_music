package com.mp_music.mp_music.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mp_music.mp_music.model.PlatformModel;
import com.mp_music.mp_music.model.PublishModel;
import com.mp_music.mp_music.model.SongModel;
import com.mp_music.mp_music.repository.ISongRepository;

@Repository
public class SongRepository implements ISongRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<SongModel> readAll() {
        var query = "SELECT * FROM songs";

        return jdbc.query(query, new BeanPropertyRowMapper<SongModel>(SongModel.class));
    }

    @Override
    public String update(SongModel model, int id, String name, String artist, int year, String genre) {

        var findSong = "SELECT * FROM songs WHERE id = ?";

        List<SongModel> song = jdbc.query(findSong, new BeanPropertyRowMapper<SongModel>(SongModel.class), id);

        if (name == "") {
            name = song.get(0).getName();
        }

        if (artist == "") {
            artist = song.get(0).getArtist();
        }

        if (genre == "") {
            genre = song.get(0).getGenre();
        }

        var query = "UPDATE songs SET name = ?, artist = ?, year = ?, genre = ? WHERE id = ?";

        jdbc.update(query, name, artist, year, genre, id);

        return "Update song successful.";
    }

    @Override
    public String delete(int id) {
        var queryPublish = "DELETE FROM publish WHERE song_id = ?";

        var querySong = "DELETE FROM songs WHERE id = ?";

        jdbc.update(queryPublish, id);
        jdbc.update(querySong, id);

        return "Delete song successful.";
    }

    @Override
    public List<String> findPlatforms(int songId) {

        var queryPublish = "SELECT * FROM publish WHERE song_id = ?";

        List<PublishModel> plat = jdbc.query(queryPublish, new BeanPropertyRowMapper<PublishModel>(PublishModel.class),
                songId);

        List<String> platforms = new ArrayList<>();

        for (PublishModel publishModel : plat) {
            int platId = publishModel.getPlatformId();

            var queryPlatform = "SELECT * FROM platforms WHERE id = ?";

            List<PlatformModel> platModel = jdbc.query(queryPlatform,
                    new BeanPropertyRowMapper<PlatformModel>(PlatformModel.class), platId);

            platforms.add(platModel.get(0).getName());
        }

        return platforms;
    }

}
