package com.mp_music.mp_music.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mp_music.mp_music.model.InsertSongModel;
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

    @PostMapping("/user/updateSong")
    public String update(@RequestBody InsertSongModel model, @RequestParam int id) {
        return songServ.update(model, id);
    }

    @PostMapping("/admin/deleteSong")
    public String delete(@RequestParam int id, HttpServletResponse response) throws Exception {
        songServ.delete(id);
        response.sendRedirect("/");
        return "";
    }
}
