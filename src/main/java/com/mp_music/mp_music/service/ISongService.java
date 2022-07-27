package com.mp_music.mp_music.service;

import java.util.List;

import com.mp_music.mp_music.model.SongModel;

public interface ISongService {

    public List<SongModel> readAll();

    public String update(SongModel model, int id, String name, String artist, int year, String genre);

    public String delete(int id);

}
