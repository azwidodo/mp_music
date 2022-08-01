package com.mp_music.mp_music.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;

public interface IPublishService {

    public List<ReadModel> readAll();

    public ReadModel readBySongId(int id);

    public Page<ReadModel> readAllPagination(Pageable page);

    public String insert(InsertSongModel model);

    public List<ReadModel> readByPlatform(String platform);

    public Page<ReadModel> readByPlatform(String platform, Pageable page);

}
