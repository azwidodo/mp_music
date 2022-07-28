package com.mp_music.mp_music.model;

import java.util.List;

import lombok.Data;

@Data
public class InsertSongModel {
    // private int id;

    private String name;

    private String artist;

    private int year;

    private String genre;

    private List<String> platforms;
}
