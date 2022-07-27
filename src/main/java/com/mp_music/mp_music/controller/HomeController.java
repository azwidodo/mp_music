package com.mp_music.mp_music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.service.IPublishService;

@Controller
public class HomeController {

    @Autowired
    IPublishService publishServ;

    @RequestMapping("/")
    public String home() {
        return "/home";
    }

    @RequestMapping("/all")
    public String all(Model model) {
        List<ReadModel> allList = publishServ.readAll();

        model.addAttribute("all", allList);
        return "/all";
    }

    @RequestMapping("/insert")
    public String insertSongModal() {
        return "/insert";
    }

}
