package net.penguincoders.doit.Model;

public class ToDoModel {
    private int id, status;
    private String task;
    private String timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    private char type;
    public char getType() {
        return type;  // Ensure it always returns a char
    }

    public void setType(char type) {
        this.type = type;
    }
}
