package com.mp_music.mp_music.model;

import java.util.List;

import lombok.Data;

@Data
public class ReadModel {

    private int id;

    private String songName;

    private String artist;

    private int year;

    private String genre;

    private List<String> platforms;

    private String uniquePlatform;

    public ReadModel(int id, String songName, String artist, int year, String genre, List<String> platforms,
            String uniquePlatform) {
        this.id = id;
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
        this.platforms = platforms;
        this.uniquePlatform = uniquePlatform;
    }
}
