package com.mp_music.mp_music.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.model.SongModel;
import com.mp_music.mp_music.service.IPublishService;

@RestController
@RequestMapping("/api")
public class PublishController {
    @Autowired
    IPublishService publishServ;

    @GetMapping("/readAll")
    public List<ReadModel> readAll() {
        return publishServ.readAll();
    }

    @GetMapping("/findPlatformId")
    public int findPlatformId(@RequestParam String platformName) {
        return publishServ.findPlatformId(platformName);
    }

    @PostMapping("/insert")
    public String insert(@RequestBody InsertSongModel model) {
        return publishServ.insert(model);
    }

    @GetMapping("/readByPlatform")
    public List<SongModel> readByPlatform(@RequestParam String platform) {
        return publishServ.readByPlatform(platform);
    }
}
