package com.sleeptracker.sleeptracker.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sleeptracker.sleeptracker.models.Sleep;
import com.sleeptracker.sleeptracker.repositories.SleepRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SleepController {
    @Autowired
    private SleepRepository sleepRepository;

    @PostMapping(value = "/sleeps-api/sleep")
    public Sleep createSleep(@RequestBody Sleep entity) {
        entity.setCreatedAt();
        sleepRepository.save(entity);
        return entity;
    }

    @PostMapping(value = "/sleeps-api/sleeps/{userId}/{sleepId}")
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

    @GetMapping(value = "/sleeps-api/sleeps/{userId}")
    public List<Sleep> getSleepsByUserId(@PathVariable String userId) {
        return sleepRepository.findByUserId(userId);
    }

    @GetMapping(value = "/sleeps-api/sleeps/{userId}/{sleepId}")
    public Optional<Sleep> getSleepByID(@PathVariable String userId, @PathVariable Long sleepId) {
        return sleepRepository.findById(sleepId);
    }

    @DeleteMapping(value = "/sleeps-api/sleeps/{userId}/{sleepId}/{noteIndex}")
    public ResponseEntity<Sleep> deleteNote(
            @PathVariable String userId,
            @PathVariable Long sleepId,
            @PathVariable int noteIndex) {
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

    @DeleteMapping(value = "sleeps-api/sleeps/{userId}/{sleepId}")
    public ResponseEntity<Sleep> deleteSleep(
            @PathVariable String userId,
            @PathVariable Long sleepId) {
        Optional<Sleep> sleepOptional = sleepRepository.findById(sleepId);

        if (sleepOptional.isPresent()) {
            Sleep sleep = sleepOptional.get();

            if (!userId.equals(sleep.getUserId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else if (userId.equals(sleep.getUserId())) {
                sleepRepository.deleteById(sleepId);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

