package com.sleeptracker.sleeptracker.controllers;

import com.sleeptracker.sleeptracker.services.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import com.sleeptracker.sleeptracker.models.Sleep;
import com.sleeptracker.sleeptracker.repositories.SleepRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/sleeps-api")
public class SleepController {
    @Autowired
    private SleepRepository sleepRepository;

    @PostMapping(value = "/sleep")
    public Sleep createSleep(@RequestBody Sleep entity, HttpServletRequest request) {
        entity.setCreatedAt();
        sleepRepository.save(entity);
        return entity;
    }

    @PostMapping(value = "/sleeps/{userId}/{sleepId}")
    public ResponseEntity<Sleep> addNote(
            @PathVariable String userId,
            @PathVariable Long sleepId,
            @RequestBody String note) {
        Optional<Sleep> sleepOptional = sleepRepository.findById(sleepId);
        if (sleepOptional.isPresent()) {
            Sleep sleep = sleepOptional.get();
            if (!userId.equals(sleep.getUserId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            sleep.getNotes().add(note);
            Sleep updatedSleep = sleepRepository.save(sleep);
            return new ResponseEntity<>(updatedSleep, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/sleeps/{userId}")
    public Object getSleepsByUserId(@PathVariable String userId, HttpServletRequest request) {
        if (!isCurrentUser(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return sleepRepository.findByUserId(userId);
    }

    @GetMapping(value = "/sleeps/{userId}/{sleepId}")
    public Object getSleepByID(@PathVariable String userId, @PathVariable Long sleepId, HttpServletRequest request) {
        if (!isCurrentUser(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return sleepRepository.findById(sleepId);
    }

    @DeleteMapping(value = "/sleeps/{userId}/{sleepId}/{noteIndex}")
    public ResponseEntity<Sleep> deleteNote(
            @PathVariable String userId,
            @PathVariable Long sleepId,
            @PathVariable int noteIndex,
            HttpServletRequest request) {
        if (!isCurrentUser(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Sleep> sleepOptional = sleepRepository.findById(sleepId);

        if (sleepOptional.isPresent()) {
            Sleep sleep = sleepOptional.get();

            if (!userId.equals(sleep.getUserId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (noteIndex >= 0 && noteIndex < sleep.getNotes().size()) {
                sleep.getNotes().remove(noteIndex);
                Sleep updatedSleep = sleepRepository.save(sleep);
                return new ResponseEntity<>(updatedSleep, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/sleeps/{userId}/{sleepId}")
    public ResponseEntity<Sleep> deleteSleep(
            @PathVariable String userId,
            @PathVariable Long sleepId,
            HttpServletRequest request) {
        if (!isCurrentUser(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Sleep> sleepOptional = sleepRepository.findById(sleepId);

        if (sleepOptional.isPresent()) {
            Sleep sleep = sleepOptional.get();

            if (!userId.equals(sleep.getUserId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                sleepRepository.deleteById(sleepId);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    private SessionService sessionService;

    public boolean isCurrentUser(String userId, HttpServletRequest request) {
        String sessionToken = getSessionToken(request);
        if (sessionToken != null) {
            String currentUserId = sessionService.getUserIdBySessionToken(sessionToken);
            if (currentUserId != null && currentUserId.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private String getSessionToken(HttpServletRequest request) {
        String sessionToken = null;
        String cookieName1 = "__Secure-next-auth.session-token";
        String cookieName2 = "next-auth.session-token";

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName1) || cookie.getName().equals(cookieName2)) {
                    sessionToken = cookie.getValue();
                    break;
                }
            }
        }

        return sessionToken;
    }

}

