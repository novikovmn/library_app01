package org.example.library_app.model;

import javax.validation.constraints.*;
import java.util.Objects;

public class Person {

    private int id;

    @NotBlank(message = "This field shouldn't be blank.")
    @Pattern(regexp = "[a-zA-Zа-яА-Я ]{2,60}", message = "Incompatible symbols or too long input (should be between 2 and 60 symbols)")
    private String fullName;

    @Min(value = 1923, message = "Birth year sould be greater or equals 1923")
    @Max(value = 2009, message = "Birth year sould be less or equals 2009")
    private int birthYear;

    public Person() {
    }

    public Person(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public Person(int id, String fullName, int birthYear) {
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthYear;
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

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return birthYear == person.birthYear && Objects.equals(fullName, person.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, birthYear);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
