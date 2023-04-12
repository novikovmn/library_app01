package org.example.library_app.dao;

import org.example.library_app.model.Book;
import org.example.library_app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    public Optional<Person> getPerson(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?", new PersonMapper(), new Object[]{id})
                .stream()
                .findAny();
    }

    public Optional<Person> getPerson(String fullName){
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new PersonMapper(), fullName)
                .stream()
                .findAny();
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person (full_name, birth_year) VALUES (?, ?)", person.getFullName(), person.getBirthYear());
    }

    public void editPerson(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET full_name=?, birth_year=? WHERE person_id=?",
                person.getFullName(), person.getBirthYear(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", id);
    }


    //////////////////////////
    ////// ПОЛУЧАЕМ СПИСОК ВЗЯТЫХ КНИГ
    /////////////////////////

    public List<Book> getPersonBooks(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new BookMapper(), new Object[]{id});
    }
}
