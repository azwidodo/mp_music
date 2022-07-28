package com.mp_music.mp_music.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp_music.mp_music.repository.IPlatformRepository;
import com.mp_music.mp_music.service.IPlatformService;

@Service
public class PlatformService implements IPlatformService {
    
    @Autowired
    IPlatformRepository platformRepo;

    @Override
    public int findPlatformId(String platformName) {
        return platformRepo.findPlatformId(platformName);
    }

}
