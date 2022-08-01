package com.mp_music.mp_music.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ReadModel readBySongId(int id) {
        return publishRepo.readBySongId(id);
    }

    @Override
    public Page<ReadModel> readAllPagination(Pageable page) {
        return publishRepo.readAllPagination(page);
    }

    @Override
    public String insert(InsertSongModel model) {
        return publishRepo.insert(model);
    }

    @Override
    public List<ReadModel> readByPlatform(String platform) {
        return publishRepo.readByPlatform(platform);
    }

    @Override
    public Page<ReadModel> readByPlatform(String platform, Pageable page) {
        return publishRepo.readByPlatform(platform, page);
    }

}
