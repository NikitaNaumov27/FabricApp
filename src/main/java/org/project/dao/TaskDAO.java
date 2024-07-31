package org.project.dao;

import org.project.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.project.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class TaskDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Task> index() {
        return jdbcTemplate.query("SELECT * FROM Task", new BeanPropertyRowMapper<>(Task.class));
    }

    public Task show(int id) {
        return jdbcTemplate.query("SELECT * FROM Task WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Task.class))
                .stream().findAny().orElse(null);
    }

    public void save(Task task) {
        jdbcTemplate.update("INSERT INTO Task(task_name, deadline, difficulty) VALUES(?, ?, ?)", task.getTaskName(),
                task.getDeadline(), task.getDifficulty());
    }

    public void update(int id, Task updatedTask) {
        jdbcTemplate.update("UPDATE Task SET task_name=?, deadline=?, difficulty=? WHERE id=?", updatedTask.getTaskName(),
                updatedTask.getDeadline(), updatedTask.getDifficulty(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Task WHERE id=?", id);
    }

    // Join'им таблицы Task и Person и получаем человека, которому принадлежит задача с указанным id
    public Optional<Person> getTaskOwner(int id) {
        // Выбираем все колонки таблицы Person из объединенной таблицы
        return jdbcTemplate.query("SELECT Person.* FROM Task JOIN Person ON Task.person_id = Person.id " +
                        "WHERE Task.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    // Освбождает задачу (этот метод вызывается, когда администатор решает переназначить задачу другому сотруднику)
    public void release(int id) {
        jdbcTemplate.update("UPDATE Task SET person_id=NULL WHERE id=?", id);
    }

    // Назначает задачу сотруднику
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Task SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
}
