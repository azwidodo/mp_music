package com.mp_music.mp_music.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mp_music.mp_music.model.AuthenticationRequest;
import com.mp_music.mp_music.model.AuthenticationResponse;
import com.mp_music.mp_music.model.InsertSongModel;
import com.mp_music.mp_music.model.ReadModel;
import com.mp_music.mp_music.service.IPublishService;
import com.mp_music.mp_music.service.ISongService;
import com.mp_music.mp_music.util.JwtUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@Controller
public class HomeController {

    @Autowired
    IPublishService publishServ;

    @Autowired
    ISongService songServ;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/")
    public String showPage(Model model, @RequestParam(defaultValue = "0") int page) {

        PageRequest pr = PageRequest.of(page, 6);

        model.addAttribute("songs", publishServ.readAllPagination(pr));
        model.addAttribute("currentPage", page);

        return "/home";
    }

    @RequestMapping("/readByPlatform")
    public String readByPlatform(Model model, @RequestParam String platform) {

        model.addAttribute("songs", publishServ.readByPlatform(platform));

        return "/platforms";
    }

    @GetMapping("/readById")
    @ResponseBody
    public ReadModel readById(@RequestParam int id) {
        return publishServ.readBySongId(id);
    }

    @PostMapping("/admin/save")
    public String save(InsertSongModel song) {

        publishServ.insert(song);

        return "redirect:/";
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

    @GetMapping("/refreshToken")
    public ResponseEntity<?> createRefreshToken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtUtil.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

    @PostMapping("/admin/insertSubmit")
    public String insertSong(HttpServletRequest request) {
        InsertSongModel model = new InsertSongModel();

        model.setName(request.getParameter("name"));
        model.setArtist(request.getParameter("artist"));
        model.setYear(Integer.valueOf(request.getParameter("year")));
        model.setGenre(request.getParameter("genre"));

        List<String> platforms = new ArrayList<String>();

        if (request.getParameter("spotify") != null) {
            platforms.add("Spotify");
        }

        if (request.getParameter("youtube") != null) {
            platforms.add("Youtube Music");
        }

        if (request.getParameter("apple") != null) {
            platforms.add("Apple Music");
        }

        if (request.getParameter("deezer") != null) {
            platforms.add("Deezer");
        }

        if (request.getParameter("joox") != null) {
            platforms.add("JOOX");
        }

        model.setPlatforms(platforms);

        publishServ.insert(model);

        return "redirect:/";
    }

    @PostMapping("/user/updateSubmit")
    public String updateSong(HttpServletRequest request, @RequestParam int id) {
        InsertSongModel model = new InsertSongModel();

        model.setName(request.getParameter("name"));
        model.setArtist(request.getParameter("artist"));
        model.setYear(Integer.valueOf(request.getParameter("year")));
        model.setGenre(request.getParameter("genre"));

        List<String> platforms = new ArrayList<String>();

        if (request.getParameter("spotify") != null) {
            platforms.add("Spotify");
        }

        if (request.getParameter("youtube") != null) {
            platforms.add("Youtube Music");
        }

        if (request.getParameter("apple") != null) {
            platforms.add("Apple Music");
        }

        if (request.getParameter("deezer") != null) {
            platforms.add("Deezer");
        }

        if (request.getParameter("joox") != null) {
            platforms.add("JOOX");
        }

        model.setPlatforms(platforms);

        songServ.update(model, id);

        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }
}
