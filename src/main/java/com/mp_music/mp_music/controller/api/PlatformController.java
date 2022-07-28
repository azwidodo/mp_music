package com.mp_music.mp_music.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mp_music.mp_music.service.IPlatformService;

@RestController
@RequestMapping("/api")
public class PlatformController {

    @Autowired
    IPlatformService platformServ;

    @GetMapping("/findPlatformId")
    public int findPlatformId(@RequestParam String platformName) {
        return platformServ.findPlatformId(platformName);
    }
}
