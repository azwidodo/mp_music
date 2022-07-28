package com.mp_music.mp_music.repository;

import java.util.List;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;

public interface IPublishRepository {

    public List<ReadModel> readAll();


    public String insert(InsertSongModel model);

    public List<ReadModel> readByPlatform(String platform);

}
