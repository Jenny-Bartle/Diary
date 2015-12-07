package com.jenny.diary;

import java.sql.Timestamp;

/**
 * Created by Jenny on 30/07/2015.
 */
public class Task {

    private long id;
    private String heading;
    private String details;
    private Timestamp timestamp;
    private Timestamp dueDate;

    public Task(long id, String heading, String details, Timestamp timestamp){
        this.id = id;
        this.heading = heading;
        this.details = details;
        this.timestamp = timestamp;
    }

    public Task(long id, String heading, String details, Timestamp timestamp, Timestamp dueDate){
        this.id = id;
        this.heading = heading;
        this.details = details;
        this.timestamp = timestamp;
        this.dueDate = dueDate;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public String getHeading() {
        return heading;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }
}
