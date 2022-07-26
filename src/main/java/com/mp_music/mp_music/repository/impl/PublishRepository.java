package com.mp_music.mp_music.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.model.SongModel;
import com.mp_music.mp_music.repository.IPlatformRepository;
import com.mp_music.mp_music.repository.IPublishRepository;
import com.mp_music.mp_music.repository.ISongRepository;

@Repository
public class PublishRepository implements IPublishRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    private ISongRepository songRepo;
    
    @Autowired
    private IPlatformRepository platformRepo;

    @Override
    public List<ReadModel> readAll() {
        List<SongModel> songs = songRepo.readAll();

        List<ReadModel> allList = new ArrayList<>();

        for (SongModel song : songs) {
            List<String> platforms = songRepo.findPlatforms(song.getId());
            allList.add(new ReadModel(song.getId(), song.getName(), song.getArtist(), song.getYear(), song.getGenre(),
                    platforms, ""));
        }

        return allList;
    }

    @Override
    public ReadModel readBySongId(int id) {
        SongModel song = songRepo.readById(id);

        List<String> platforms = songRepo.findPlatforms(song.getId());

        ReadModel read = new ReadModel(song.getId(), song.getName(), song.getArtist(), song.getYear(), song.getGenre(),
                platforms, "");

        return read;
    }

    @Override
    public Page<ReadModel> readAllPagination(Pageable page) {

        var query = "SELECT * FROM songs ORDER BY id LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset();

        List<SongModel> songs = jdbc.query(query, new BeanPropertyRowMapper<SongModel>(SongModel.class));

        List<ReadModel> allList = new ArrayList<>();

        for (SongModel song : songs) {
            List<String> platforms = songRepo.findPlatforms(song.getId());
            allList.add(new ReadModel(song.getId(), song.getName(), song.getArtist(),
                    song.getYear(), song.getGenre(),
                    platforms, ""));
        }

        return new PageImpl<ReadModel>(allList, page, readAll().size());
    }

    @Override
    public String insert(InsertSongModel model) {

        var querySong = "INSERT INTO songs(name, artist, year, genre) VALUES (?, ?, ?, ?)";

        jdbc.update(querySong, new Object[] { model.getName(), model.getArtist(), model.getYear(), model.getGenre() });

        List<SongModel> songsList = songRepo.readAll();

        int newId = songsList.get(songsList.size() - 1).getId();

        for (String platform : model.getPlatforms()) {
            int platformId = platformRepo.findPlatformId(platform);

            var queryPublish = "INSERT INTO publish(song_id, platform_id) VALUES (?, ?)";

            jdbc.update(queryPublish, new Object[] { newId, platformId });
        }

        return "Insert data successful.";
    }

    @Override
    public List<ReadModel> readByPlatform(String platform) {
        int platformId = platformRepo.findPlatformId(platform);

        var query = "SELECT s.id, s.name, s.artist, s.year, s.genre FROM songs s "
                + "RIGHT JOIN publish p ON p.song_id = s.id "
                + "WHERE p.platform_id = ? "
                + "ORDER BY s.id";

        List<SongModel> songs = jdbc.query(query, new BeanPropertyRowMapper<SongModel>(SongModel.class), platformId);

        List<ReadModel> allList = new ArrayList<>();

        for (SongModel song : songs) {
            List<String> platforms = songRepo.findPlatforms(song.getId());
            allList
                    .add(new ReadModel(song.getId(), song.getName(), song.getArtist(), song.getYear(), song.getGenre(),
                            platforms, platform));
        }

        return allList;
    }

    @Override
    public Page<ReadModel> readByPlatform(String platform, Pageable page) {
        int platformId = platformRepo.findPlatformId(platform);

        var query = "SELECT s.id, s.name, s.artist, s.year, s.genre FROM songs s "
                + "RIGHT JOIN publish p ON p.song_id = s.id "
                + "WHERE p.platform_id = ? "
                + "ORDER BY s.id "
                + "LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset();

        List<SongModel> songs = jdbc.query(query, new BeanPropertyRowMapper<SongModel>(SongModel.class), platformId);

        List<ReadModel> allList = new ArrayList<>();

        for (SongModel song : songs) {
            List<String> platforms = songRepo.findPlatforms(song.getId());
            allList
                    .add(new ReadModel(song.getId(), song.getName(), song.getArtist(), song.getYear(), song.getGenre(),
                            platforms, platform));
        }

        return new PageImpl<ReadModel>(allList, page, readAll().size());
    }
}
