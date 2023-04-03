package com.sleeptracker.sleeptracker.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sleeptracker.sleeptracker.models.Sleep;
import com.sleeptracker.sleeptracker.repositories.SleepRepository;

import java.util.List;
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

    @PostMapping(value = "/sleep")
    public Sleep postMethodName(@RequestBody Sleep entity) {
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
            System.out.println(sleep);
            Sleep updatedSleep = sleepRepository.save(sleep);
            return new ResponseEntity<>(updatedSleep, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/sleeps/{userId}")
    public List<Sleep> getSleepsByUserId(@PathVariable String userId) {
        return sleepRepository.findByUserId(userId);
    }

    @GetMapping(value = "/sleeps/{userId}/{sleepId}")
    public Optional<Sleep> getSleepByID(@PathVariable String userId, @PathVariable Long sleepId) {
        return sleepRepository.findById(sleepId);
    }

    @DeleteMapping(value = "/sleeps/{userId}/{sleepId}/{noteIdx}")
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
}