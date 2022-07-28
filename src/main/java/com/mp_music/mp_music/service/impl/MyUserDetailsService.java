package com.mp_music.mp_music.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mp_music.mp_music.model.UserModel;
import com.mp_music.mp_music.repository.impl.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepo.getUserModel(username);

        GrantedAuthority authority = new SimpleGrantedAuthority(userModel.getAuthority());

        UserDetails userDetails = new User(userModel.getUsername(), userModel.getPassword(),
                Arrays.asList(authority));

        return userDetails;
    }

}
