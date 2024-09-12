package org.example.server.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/rehearsal/exit")
    @SendTo("/exit")
    public String adminExit() {
        return "Admin has exited";
    }

    @MessageMapping("/rehearsal/join")
    @SendTo("/join")
    public String broadcastJoin(String song) {
        return song;
    }

    @MessageMapping("song/player")
    @SendTo("/song/player")
    public String broadcastSelectedSongPlayer(String song) {
        return song;
    }

    @MessageMapping("song/singer")
    @SendTo("/song/singer")
    public String broadcastSelectedSongSinger(String song) {
        return song;
    }
}
