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
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth, position) VALUES(?, ?, ?)", person.getFullName(),
                person.getYearOfBirth(), person.getPosition());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=?, position=? WHERE id=?", updatedPerson.getFullName(),
                updatedPerson.getYearOfBirth(), updatedPerson.getPosition(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    // Для валидации уникальности ФИО
    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    // Здесь Join не нужен. И так уже получили человека с помощью отдельного метода
    public List<Task> getTasksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Task WHERE person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Task.class));
    }
}