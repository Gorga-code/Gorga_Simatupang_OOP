package com.gorga.backend.controller;

import com.gorga.backend.Service.PlayerService;
import com.gorga.backend.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable UUID playerId) {
        Optional<Player> player = playerService.getPlayerById(playerId);
        if (player.isPresent()) {
            ResponseEntity.ok(player.get());
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getPlayerByUsername(@PathVariable String username) {
        Optional<Player> player = playerService.getPlayerByUsername(username);
        if (player.isPresent()) {
            ResponseEntity.ok(player.get());
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @RequestBody
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        playerService.createPlayer(player)
    }

}
