package org.example.server.controllers;

import org.example.server.logic.services.UserServiceMDB;
import org.example.server.logic.services.SongService;
import org.example.server.logic.song.Song;
import org.example.server.logic.users.User;
import org.example.server.logic.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminAPIController {
    private UserServiceMDB userService;
    private SimpMessagingTemplate messagingTemplate;
    private SongService songService;

    @Autowired
    public AdminAPIController(UserServiceMDB userService, SimpMessagingTemplate messagingTemplate, SongService songService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
        this.songService = songService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerAdmin(@RequestBody User user) {
        user.setRole(UserRole.ADMIN);
        User registeredAdmin = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredAdmin);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitRehearsal() {
        notifyUsersExit();
        return ResponseEntity.ok("Rehearsal has been exited by the admin.");
    }

    private void notifyUsersExit() {
        messagingTemplate.convertAndSend("/send/exit", "Admin has exited the rehearsal. Please return to the main page.");
    }


    @PostMapping("/search")
    public List<Song> searchSongs(@RequestBody String query) {
        List<Song> foundSongs = songService.searchForSongs(query);
        return foundSongs;
    }

    @PostMapping("/movetolive")
    public ResponseEntity<String> ready(@RequestBody Song selectedSong) throws IOException {
        messagingTemplate.convertAndSend("/send/join", selectedSong);
        return ResponseEntity.ok("moved");
    }

    @PostMapping("/show")
    public ResponseEntity<String> selectSong(@RequestBody Song selectedSong) throws IOException {
        String songDataWithChords = songService.getSongWithChords(selectedSong.getTitle(), selectedSong.getArtist());
        String songData = songService.getSongWithLyrics(selectedSong.getTitle(), selectedSong.getArtist());

        messagingTemplate.convertAndSend("/send/song/player", songDataWithChords);

        messagingTemplate.convertAndSend("/send/song/singer", songData);



        return ResponseEntity.ok("sent");
    }




}
