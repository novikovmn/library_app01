package org.example.library_app.model;

import javax.validation.constraints.*;
import java.util.Objects;

public class Book {

    private int id;

    @NotBlank(message = "This field shouldn't be blank.")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]{2,255}", message = "Incompatible symbols or too long input (should be between 2 and 255 symbols)")
    private String title;

    @NotBlank(message = "This field shouldn't be blank.")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]{2,60}", message = "Incompatible symbols or too long input (should be between 2 and 60 symbols)")
    private String authorName;

    @Min(value = 0, message = "Should be greater or equals 0")
    private int releaseYear;
    private int personId;

    public Book() {
    }

    public Book(String title, String authorName, int releaseYear, int personId) {
        this.title = title;
        this.authorName = authorName;
        this.releaseYear = releaseYear;
        this.personId = personId;
    }

    public Book(int id, String title, String authorName, int releaseYear, int personId) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.releaseYear = releaseYear;
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return releaseYear == book.releaseYear && Objects.equals(title, book.title) && Objects.equals(authorName, book.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authorName, releaseYear);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", releaseYear=" + releaseYear +
                ", personId=" + personId +
                '}';
    }
}
