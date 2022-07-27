package com.mp_music.mp_music.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.PlatformModel;
import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.model.SongModel;
import com.mp_music.mp_music.repository.IPublishRepository;
import com.mp_music.mp_music.repository.ISongRepository;

@Repository
public class PublishRepository implements IPublishRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    private ISongRepository songRepo;

    @Override
    public List<ReadModel> readAll() {
        List<SongModel> songs = songRepo.readAll();

        List<ReadModel> allList = new ArrayList<>();

        for (SongModel song : songs) {
            List<String> platforms = songRepo.findPlatforms(song.getId());
            allList.add(new ReadModel(song.getId(), song.getName(), song.getArtist(), song.getYear(), song.getGenre(),
                    platforms));
        }

        return allList;
    }

    @Override
    public int findPlatformId(String platformName) {
        var platIdQuery = "SELECT * FROM platforms WHERE name = ?";
        List<PlatformModel> platIdList = jdbc.query(platIdQuery,
                new BeanPropertyRowMapper<PlatformModel>(PlatformModel.class), platformName);
        return platIdList.get(0).getId();
    }

    @Override
    public String insert(InsertSongModel model) {

        var querySong = "INSERT INTO songs(name, artist, year, genre) VALUES (?, ?, ?, ?)";

        jdbc.update(querySong, new Object[] { model.getName(), model.getArtist(), model.getYear(), model.getGenre() });

        List<SongModel> songsList = songRepo.readAll();

        int newId = songsList.get(songsList.size() - 1).getId();

        for (String platform : model.getPlatforms()) {
            int platformId = this.findPlatformId(platform);

            var queryPublish = "INSERT INTO publish(song_id, platform_id) VALUES (?, ?)";

            jdbc.update(queryPublish, new Object[] { newId, platformId });
        }

        return "Insert data successful.";
    }

    @Override
    public List<SongModel> readByPlatform(String platform) {
        int platformId = this.findPlatformId(platform);

        var query = "SELECT s.id, s.name, s.artist, s.year, s.genre FROM songs s "
                + "RIGHT JOIN publish p ON p.song_id = s.id "
                + "WHERE p.platform_id = ?";

        return jdbc.query(query, new BeanPropertyRowMapper<SongModel>(SongModel.class), platformId);
    }

}
