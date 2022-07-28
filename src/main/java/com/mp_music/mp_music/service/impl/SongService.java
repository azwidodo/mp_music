package com.mp_music.mp_music.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.SongModel;
import com.mp_music.mp_music.repository.ISongRepository;
import com.mp_music.mp_music.service.ISongService;

@Service
public class SongService implements ISongService {

    @Autowired
    ISongRepository songRepo;

    @Override
    public List<SongModel> readAll() {
        return songRepo.readAll();
    }

    @Override
    public String update(InsertSongModel model, int id) {
        return songRepo.update(model, id);
    }

    @Override
    public String delete(int id) {
        return songRepo.delete(id);
    }

}
