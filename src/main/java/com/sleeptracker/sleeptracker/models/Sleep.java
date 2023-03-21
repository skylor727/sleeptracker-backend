package com.sleeptracker.sleeptracker.models;

import java.util.Date;
import javax.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "sleep")
public class Sleep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Date timeAsleep;

    @Column(nullable = true)
    private Date timeAwake;

    @Column(nullable = true)
    private Date calculatedTime;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeAsleep() {
        return this.timeAsleep;
    }

    public void setTimeAsleep(Date timeAsleep) {
        this.timeAsleep = timeAsleep;
    }

    public Date getTimeAwake() {
        return this.timeAwake;
    }

    public void setTimeAwake(Date timeAwake) {
        this.timeAwake = timeAwake;
    }

    public Date getCalculatedTime() {
        return this.calculatedTime;
    }

    public void setCalculatedTime(Date calculatedTime) {
        this.calculatedTime = calculatedTime;
    }

}
