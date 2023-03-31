package com.sleeptracker.sleeptracker.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sleeptracker.sleeptracker.models.Sleep;
import com.sleeptracker.sleeptracker.repositories.SleepRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println(entity.toString());
        entity.setCreatedAt();
        sleepRepository.save(entity);
        return entity;
    }

    @GetMapping(value = "/sleeps/{userId}")
    public List<Sleep> getSleepsByUserId(@PathVariable String userId) {
        return sleepRepository.findByUserId(userId);
    }
}
