package com.mp_music.mp_music.repository;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.SongModel;

import java.util.List;

public interface ISongRepository {

    public List<SongModel> readAll();

    public String update(InsertSongModel model, int id);

    public String delete(int id);

    public List<String> findPlatforms(int songId);

}
