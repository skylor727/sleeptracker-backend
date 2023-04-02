package com.sleeptracker.sleeptracker.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "sleep")
public class Sleep {

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

    public List<String> getNotes() {
        return this.notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        this.createdAt = LocalDateTime.now().format(formatter);
    };

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setwakeUp(String wakeUp) {
        this.wakeUp = wakeUp;
    }

    public String getwakeUp() {
        return this.wakeUp;
    }

    public String getgoToSleep() {
        return this.goToSleep;
    }

    public void setgoToSleep(String goToSleep) {
        this.goToSleep = goToSleep;
    }

    public String getCalculatedTime() {
        return this.calculatedTime;
    }

    public void setCalculatedTime(String calculatedTime) {
        this.calculatedTime = calculatedTime;
    }

    public Sleep() {
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
    }

    @Override
    public String toString() {
        String notesString = String.join(", ", this.notes);
        return "goToSleep " + this.goToSleep + " wakeUp " + this.wakeUp + " calculatedTime "
                + this.calculatedTime + " userid " + this.userId + " notes [" + notesString + "]";
    }
}
