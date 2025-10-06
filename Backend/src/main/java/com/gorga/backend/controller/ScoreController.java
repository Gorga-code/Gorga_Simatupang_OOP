package com.gorga.backend.controller;

import com.gorga.backend.model.Score; //sesuaikan
import com.gorga.backend.service.ScoreService; //sesuaikan
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping
    public ResponseEntity<Score> createScore(@RequestBody Score score){
        try{
            Score createdScore = scoreService.createScore(score);
            return ResponseEntity.status(HttpStatus.CREATED);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Score>> getAllScores(){
        List<Score> scores = scoreService.getAllScores();
        return ResponseEntity.ok();
    }

    @GetMapping("/{scoreId}")
    ResponseEntity<?> getScoreById (@PathVariable String scoreId){
        Score scores = scoreService.getScoreById(scoreId);
        if (score.isPresent()){
            return ResponseEntity.ok();
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }

}
