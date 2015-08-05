package com.jenny.diary;

import java.sql.Timestamp;

/**
 * Created by Jenny on 30/07/2015.
 */
public class Task {
    //private variables
    long _id;

    String _heading;
    String _details;
    Timestamp _timestamp;

    // Empty constructor
    public Task(){

    }
    // constructor
    public Task(long id, String heading, String details, Timestamp timestamp){
        this._id = id;
        this._heading = heading;
        this._details = details;
        this._timestamp = timestamp;
    }

    public long getID(){
        return this._id;
    }

    public void setID(long id){
        this._id = id;
    }

    public String getDetails() {
        return _details;
    }

    public void setDetails(String _details) {
        this._details = _details;
    }

    public String getHeading() {
        return _heading;
    }

    public void setHeading(String _heading) {
        this._heading = _heading;
    }

    public Timestamp getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(Timestamp _timestamp) {
        this._timestamp = _timestamp;
    }

}
