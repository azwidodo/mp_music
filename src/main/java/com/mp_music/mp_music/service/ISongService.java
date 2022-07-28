package com.mp_music.mp_music.service;

import java.util.List;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.SongModel;

public interface ISongService {

    public List<SongModel> readAll();

    public String update(InsertSongModel model, int id);

    public String delete(int id);

}
