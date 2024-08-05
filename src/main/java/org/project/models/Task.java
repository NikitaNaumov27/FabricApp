package org.project.models;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Task {
    private int id;

    @NotEmpty(message = "Название задачи не должно быть пустым")
    @Size(min = 2, max = 60, message = "Название задачи должно быть от 2 до 60 символов длиной")
    private String taskName;

    @Min(value = 1, message = "Срок выполнения должен быть больше, чем 1 день")
    private int deadline;

    @Min(value = 1, message = "Сложность задачи не может быть меньше 1 (Легкая)")
    @Max(value = 3, message = "Сложность задачи не может быть больше 3 (Сложная)")
    private int difficulty;

    public Task() {
    }

    public Task(String taskName, int deadline, int difficulty) {
        this.taskName = taskName;
        this.deadline = deadline;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}