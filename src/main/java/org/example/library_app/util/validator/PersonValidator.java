package org.example.library_app.util.validator;

import org.example.library_app.dao.PersonDAO;
import org.example.library_app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> optionalPerson = personDAO.getPerson(person.getFullName());
        if (optionalPerson.isPresent()) {
            errors.rejectValue("fullName", "", "Person with this fullName is already exists");
        }
    }
}
