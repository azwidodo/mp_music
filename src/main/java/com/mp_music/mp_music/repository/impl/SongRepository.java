package com.mp_music.mp_music.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.PlatformModel;
import com.mp_music.mp_music.model.PublishModel;
import com.mp_music.mp_music.model.SongModel;
import com.mp_music.mp_music.repository.IPlatformRepository;
import com.mp_music.mp_music.repository.ISongRepository;

@Repository
public class SongRepository implements ISongRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    IPlatformRepository platformRepo;

    @Override
    public List<SongModel> readAll() {
        var query = "SELECT * FROM songs";

        return jdbc.query(query, new BeanPropertyRowMapper<SongModel>(SongModel.class));
    }

    @Override
    public String update(InsertSongModel model, int id) {

        // var findSong = "SELECT * FROM songs WHERE id = ?";

        // List<SongModel> song = jdbc.query(findSong, new
        // BeanPropertyRowMapper<SongModel>(SongModel.class), id);

        var querySong = "UPDATE songs SET name = ?, artist = ?, year = ?, genre = ? WHERE id = ?";

        jdbc.update(querySong, model.getName(), model.getArtist(), model.getYear(), model.getGenre(), id);

        var queryPlatform = "DELETE FROM publish WHERE song_id = ?";

        jdbc.update(queryPlatform, id);

        for (String platform : model.getPlatforms()) {
            int platformId = platformRepo.findPlatformId(platform);

            var queryPublish = "INSERT INTO publish(song_id, platform_id) VALUES (?, ?)";

            jdbc.update(queryPublish, new Object[] { id, platformId });
        }

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
