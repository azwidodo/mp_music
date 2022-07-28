package com.mp_music.mp_music.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mp_music.mp_music.model.AuthenticationRequest;
import com.mp_music.mp_music.model.AuthenticationResponse;
import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.service.IPublishService;
import com.mp_music.mp_music.util.JwtUtil;

@Controller
public class HomeController {

    @Autowired
    IPublishService publishServ;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/")
    public String home() {
        return "/home";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest)
            throws Exception {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping("/user")
    @ResponseBody
    public String user() {
        return "<h1>Welcome User</h1>";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin() {
        return "<h1>Welcome Admin</h1>";
    }

    @RequestMapping("/all")
    public String all(Model model) {
        List<ReadModel> allList = publishServ.readAll();

        model.addAttribute("all", allList);
        return "/all";
    }

    @RequestMapping("/insertModal")
    public String insertModal() {
        return "/insert";
    }

    @GetMapping("/insertSubmit")
    public String insertSong(HttpServletRequest request) {
        InsertSongModel model = new InsertSongModel();

        model.setName(request.getParameter("song-name"));
        // model.setArtist(request.getParameter("artist"));
        // model.setYear(Integer.valueOf(request.getParameter("year")));
        // model.setGenre(request.getParameter("genre"));

        // List<String> platforms = new ArrayList<String>();

        System.out.println(request.getParameter("artist"));
        System.out.println(request.getParameter("year"));
        System.out.println(request.getParameter("genre"));

        return "/home";
    }

}
