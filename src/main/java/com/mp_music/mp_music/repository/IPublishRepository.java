package com.mp_music.mp_music.repository;

import java.util.List;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.model.SongModel;

public interface IPublishRepository {

    public List<ReadModel> readAll();

    public int findPlatformId(String platformName);

    public String insert(InsertSongModel model);

    public List<SongModel> readByPlatform(String platform);

}
