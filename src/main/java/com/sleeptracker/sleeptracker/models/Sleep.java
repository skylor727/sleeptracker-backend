package com.sleeptracker.sleeptracker.models;

import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "sleep")
public class Sleep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = true)
    private String wakeUp;

    @Column(nullable = false)
    private String calculationChoice;

    @Column(nullable = true)
    private String goToSleep;

    @Column(nullable = true)
    private String calculatedTime;

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
        return "goToSleep " + this.goToSleep + " wakeUp " + this.wakeUp + " calculatedTime "
                + this.calculatedTime + " userid " + this.userId;
    }
}
