package com.jenny.diary;

import java.sql.Timestamp;

/**
 * Created by Jenny on 30/07/2015.
 */
public class Task {

    long id;
    String heading;
    String details;
    Timestamp timestamp;
    Timestamp dueDate;

    public Task() {
        }

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

    public void setDetails(String _details) {
        this.details = _details;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String _heading) {
        this.heading = _heading;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp _timestamp) {
        this.timestamp = _timestamp;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp _dueDate) {
        this.dueDate = _dueDate;
    }

}
