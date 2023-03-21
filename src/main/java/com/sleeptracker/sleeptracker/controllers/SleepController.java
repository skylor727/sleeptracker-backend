package com.sleeptracker.sleeptracker.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sleeptracker.sleeptracker.models.Sleep;
import com.sleeptracker.sleeptracker.repositories.SleepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SleepController {
    @Autowired
    private SleepRepository sleepRepository;

    @PostMapping(value = "/sleep")
    public Sleep postMethodName(@RequestBody Sleep entity) {
        // TODO: process POST request
        Sleep sleep = new Sleep();

        return entity;
    }

}
