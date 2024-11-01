package com.sleeptracker.sleeptracker.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "sleep")
public class Sleep extends LoggableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private String userId;

    @Column(nullable = true)
    private String wakeUp;

    @Column(nullable = false)
    private String calculationChoice;

    @Column(nullable = true)
    private String goToSleep;

    @Column(nullable = true)
    private String calculatedTime;

    @Column(nullable = false, updatable = false)
    private String createdAt;

    @ElementCollection
    @CollectionTable(name = "sleep_notes", joinColumns = @JoinColumn(name = "sleep_id"))
    @Column(name = "note")
    private List<String> notes;

    // Constructor
    public Sleep() {
        setCreatedAt();
    }

    @JsonCreator
    public Sleep(
            @JsonProperty("userId") String userId,
            @JsonProperty("calculationChoice") String calculationChoice,
            @JsonProperty("goToSleep") String goToSleep,
            @JsonProperty("wakeUp") String wakeUp,
            @JsonProperty("calculatedTime") String calculatedTime) {
        this.userId = userId;
        this.calculationChoice = calculationChoice;
        this.goToSleep = goToSleep;
        this.wakeUp = wakeUp;
        this.calculatedTime = calculatedTime;
        setCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWakeUp() {
        return wakeUp;
    }

    public void setWakeUp(String wakeUp) {
        this.wakeUp = wakeUp;
    }

    public String getCalculationChoice() {
        return calculationChoice;
    }

    public void setCalculationChoice(String calculationChoice) {
        this.calculationChoice = calculationChoice;
    }

    public String getGoToSleep() {
        return goToSleep;
    }

    public void setGoToSleep(String goToSleep) {
        this.goToSleep = goToSleep;
    }

    public String getCalculatedTime() {
        return calculatedTime;
    }

    public void setCalculatedTime(String calculatedTime) {
        this.calculatedTime = calculatedTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.createdAt = LocalDateTime.now().format(formatter);
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        String notesString = notes != null ? String.join(", ", notes) : "No notes";
        return "Sleep Entry [ID: " + id + ", UserID: " + userId + ", GoToSleep: " + goToSleep +
                ", WakeUp: " + wakeUp + ", CalculatedTime: " + calculatedTime +
                ", CalculationChoice: " + calculationChoice +
                ", Notes: [" + notesString + "], CreatedAt: " + createdAt + "]";
    }
}
