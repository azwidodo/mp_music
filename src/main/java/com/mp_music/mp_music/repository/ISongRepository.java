package com.mp_music.mp_music.repository;

import com.mp_music.mp_music.model.SongModel;

import java.util.List;

public interface ISongRepository {

    public List<SongModel> readAll();

    public String update(SongModel model, int id, String name, String artist, int year, String genre);

    public String delete(int id);

    public List<String> findPlatforms(int songId);

}
