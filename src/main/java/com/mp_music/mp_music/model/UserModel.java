package com.mp_music.mp_music.model;

import lombok.Data;

@Data
// @Entity
// @Table(name = "users")
public class UserModel {

    private String username;
    private String password;
    private String authority;
}
