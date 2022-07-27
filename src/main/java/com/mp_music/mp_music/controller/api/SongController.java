package com.mp_music.mp_music.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mp_music.mp_music.model.SongModel;
import com.mp_music.mp_music.service.ISongService;

@RestController
@RequestMapping("/api")
public class SongController {

    @Autowired
    ISongService songServ;

    @GetMapping("/readAllSongs")
    public List<SongModel> readAll() {
        return songServ.readAll();
    }

    @PostMapping("/updateSong")
    public String update(SongModel model, @RequestParam int id, @RequestParam String name, @RequestParam String artist,
            @RequestParam int year, @RequestParam String genre) {
        return songServ.update(model, id, name, artist, year, genre);
    }

    @PostMapping("/updateSong2")
    public ModelAndView update2(@RequestParam int id) {
        ModelAndView mav = new ModelAndView("add-update-form");
        return mav;
    }

    @PostMapping("/deleteSong")
    public String delete(@RequestParam int id, HttpServletResponse response) throws Exception {
        songServ.delete(id);
        response.sendRedirect("/");
        return "/delete_modal";
    }
}
