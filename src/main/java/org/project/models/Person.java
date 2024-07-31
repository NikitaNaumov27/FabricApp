package org.project.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "ФИО сотрудника не должно быть пустым")
    @Size(min = 2, max = 50, message = "ФИО сотрудника должно быть от 2 до 50 символов длиной")
    private String fullName;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    private int yearOfBirth;

    @NotEmpty(message = "Должность сотрудника не должна быть пустой")
    @Size(min = 2, max = 50, message = "Должность сотрудника должна быть от 2 до 50 символов длиной")
    private String position;

    // Конструктор по умолчанию нужен для Spring
    public Person() {

    }



    public Person(String fullName, int yearOfBirth, String position) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}