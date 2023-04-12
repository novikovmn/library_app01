package org.example.library_app.dao;

import org.example.library_app.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthorName(rs.getString("author_name"));
        book.setReleaseYear(rs.getInt("release_year"));
        book.setPersonId(rs.getInt("person_id"));
        return book;
    }
}
