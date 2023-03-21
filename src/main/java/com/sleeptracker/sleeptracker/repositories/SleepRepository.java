package com.sleeptracker.sleeptracker.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sleeptracker.sleeptracker.models.Sleep;

@Repository
public interface SleepRepository extends CrudRepository<Sleep, Integer> {

}