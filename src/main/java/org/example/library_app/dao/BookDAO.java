package org.example.library_app.dao;

import org.example.library_app.model.Book;
import org.example.library_app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    private final PersonDAO personDAO;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }

    public List<Book> getBooks(){
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Optional<Book> getBook(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new BookMapper(), id)
                .stream()
                .findAny();
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author_name, release_year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthorName(), book.getReleaseYear());
    }

    public void editBook(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author_name=?, release_year=? WHERE book_id=?",
                book.getTitle(), book.getAuthorName(), book.getReleaseYear(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public Optional<Person> fetchThePersonByForeignKey(int personId) {
        return personDAO.getPerson(personId);
    }

    public List<Person> getPeople(){
        return personDAO.getPeople();
    }

    public void assingBook(int bookId, int personId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", personId, bookId);
    }

    public void freeBook(int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE book_id=?", bookId);
    }
}
