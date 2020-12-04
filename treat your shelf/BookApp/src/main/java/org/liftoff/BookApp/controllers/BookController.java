package org.liftoff.BookApp.controllers;

import org.liftoff.BookApp.data.BookRepository;
import org.liftoff.BookApp.models.Book;
import org.liftoff.BookApp.models.dto.BookFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping({"books"})
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    public BookController(){}

    @GetMapping({"add"})
    public String displayAddBookForm(Model model) {
        model.addAttribute(new BookFormDTO());
        return "books/add";
    }

    @PostMapping({"add"})
    public String processAddBookForm(@ModelAttribute @Valid BookFormDTO bookFormDTO, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Book");
            return "books/add";
        } else {
            Book newBook = new Book(bookFormDTO.getTitle(), bookFormDTO.getGenre() ,bookFormDTO.getAuthor());
            bookRepository.save(newBook);
            return "redirect:../";
        }
    }

    @GetMapping({"view/{bookId}"})
    public String displayViewBook(Model model, @PathVariable int bookId) {
        Optional optBook = this.bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            Book book = (Book)optBook.get();
            model.addAttribute("book", book);
            return "books/view";
        } else {
            return "redirect:../";
        }
    }

}
