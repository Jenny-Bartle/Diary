package com.jenny.diary.goal;

import com.jenny.diary.category.Category;
import com.jenny.diary.task.Task;

import java.util.List;

/**
 * Created by Jenny on 18-Aug-15.
 */
public class Goal {

    private long id;
    private String name;
    private List<Task> tasks;
    private int priority;
    private Category category;

    public Goal(){}

    public Goal(int id, String name, List<Task> tasks, int priority, Category category) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
        this.priority = priority;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
