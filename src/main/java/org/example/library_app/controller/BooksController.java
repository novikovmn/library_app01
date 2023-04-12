package org.example.library_app.controller;

import org.example.library_app.dao.BookDAO;
import org.example.library_app.model.Book;
import org.example.library_app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String getBooks(Model model){
        List<Book> books = bookDAO.getBooks();
        model.addAttribute("books", books);
        return "books/all-books";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable("id") int id, Model model){
        Optional<Book> optionalBook = bookDAO.getBook(id);
        if(optionalBook.isPresent()){
            // if the book is exists - put it to model
            Book theBook = optionalBook.get();
            model.addAttribute("book", theBook);
            // we need to find the person, who took this book (by person_id in book table)
            Optional<Person> optionalPerson = bookDAO.fetchThePersonByForeignKey(theBook.getPersonId());
            if(optionalPerson.isPresent()){
                // and put this person in model too
                Person thePerson = optionalPerson.get();
                model.addAttribute("person", thePerson);
            }else{
                // if there is no such person - put null to model
                model.addAttribute("person", null);
            }
            // also put in model list of people which we can assing the book
            List<Person> people = bookDAO.getPeople();
            model.addAttribute("people", people);
            return "books/book-page";
        }else {
            return "redirect:/books";
        }
    }

    @GetMapping("/new")
    public String addBookPage(@ModelAttribute("book") Book book){
        return "books/add-book-page";
    }

    @PostMapping()
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "books/add-book-page";
        }

        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable("id") int id, Model model){
        Optional<Book> optionalBook = bookDAO.getBook(id);
        if (optionalBook.isPresent()) {
            Book theBook = optionalBook.get();
            model.addAttribute("book", theBook);
            return "books/edit-book-page";
        }else {
            return "redirect:/books";
        }
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "books/edit-book-page";
        }

        bookDAO.editBook(id, book);
        return "redirect:/books/" + id;
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/assign")
    public String assingBook(@RequestParam("bookId") int bookId, @RequestParam("personId") int personId){
        bookDAO.assingBook(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/free")
    public String freeBook(@RequestParam("bookId") int bookId){
        bookDAO.freeBook(bookId);
        return "redirect:/books/" + bookId;
    }














}
