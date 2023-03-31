package com.sleeptracker.sleeptracker.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sleeptracker.sleeptracker.models.Sleep;

@Repository
public interface SleepRepository extends CrudRepository<Sleep, Long> {
    List<Sleep> findByUserId(String userId);
}