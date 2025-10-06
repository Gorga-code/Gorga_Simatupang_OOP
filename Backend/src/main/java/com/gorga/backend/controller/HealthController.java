package com.gorga.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
    @GetMapping("/health")
    public Map<String, Object> getHealth(){
        Map<String, Object> map = new HashMap<>();
        map.put("status","UP");
        map.put("message","Jetpack Joyride Backend is running!");
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo(){
        Map<String, Object> utama = new HashMap<>();
        utama.put("appName","CS6_Gorga_Backend");
        utama.put("version","1.0");
        utama.put("description","Backend running...");

        Map<String, String> endpoints =  new HashMap<>();
        utama.put("endpoints", endpoints);

        return utama;
    }


}

