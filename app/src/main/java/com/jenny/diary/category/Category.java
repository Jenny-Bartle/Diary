package com.jenny.diary.category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jenny on 18-Aug-15.
 */
public class Category {

    private int id;
    private String heading;
    private List<Long> tasks;

    public Category(int id, String name) {
        this.id = id;
        this.heading = name;
        this.tasks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public List<Long> getTasks() {
        return tasks;
    }

    public void addTasks(List<Long> tasks) {
        this.tasks.addAll(tasks);
    }

    public void addTask(Long task){
        tasks.add(task);
    }
}
