package com.mp_music.mp_music.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.repository.IPublishRepository;
import com.mp_music.mp_music.service.IPublishService;

@Service
public class PublishService implements IPublishService {

    @Autowired
    IPublishRepository publishRepo;

    @Override
    public List<ReadModel> readAll() {
        return publishRepo.readAll();
    }

    @Override
    public String insert(InsertSongModel model) {
        return publishRepo.insert(model);
    }

    @Override
    public List<ReadModel> readByPlatform(String platform) {
        return publishRepo.readByPlatform(platform);
    }

}
