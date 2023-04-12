package org.example.library_app.controller;

import org.example.library_app.dao.PersonDAO;
import org.example.library_app.model.Book;
import org.example.library_app.model.Person;
import org.example.library_app.util.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String allPeoplePage(Model model){
        List<Person> people = personDAO.getPeople();
        model.addAttribute("people", people);
        return "people/all-people";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model){
        Optional<Person> optionalPerson = personDAO.getPerson(id);
        if(optionalPerson.isPresent()){
            // if the person is present - put him to model and return person's page
            Person thePerson = optionalPerson.get();
            model.addAttribute("person", thePerson);
            // put books that person took from library
            List<Book> personBooks = personDAO.getPersonBooks(id);
            model.addAttribute("personBooks", personBooks);
            return "people/person-page";
        }else {
            // to simplify - if there is no person - we just return to all-people page
            return "redirect:/people";
        }
    }

    @GetMapping("/new")
    public String addPersonPage(@ModelAttribute("person") Person person){
        return "people/add-person-page";
    }

    @PostMapping()
    public String addPerson(@Valid @ModelAttribute("person") Person person,
                            BindingResult bindingResult){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/add-person-page";
        }

        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(@PathVariable("id") int id, Model model){
        Optional<Person> optionalPerson = personDAO.getPerson(id);
        if (optionalPerson.isPresent()){
            Person thePerson = optionalPerson.get();
            model.addAttribute("person", thePerson);
            return "people/edit-person-page";
        }else {
            return "redirect:/people";
        }
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id,
                            @Valid @ModelAttribute("person") Person person,
                             BindingResult bindingResult){

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit-person-page";
        }

        personDAO.editPerson(id, person);
        return "redirect:/people/" + id;
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

}
