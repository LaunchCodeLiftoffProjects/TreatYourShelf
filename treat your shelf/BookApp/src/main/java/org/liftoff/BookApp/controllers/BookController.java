package org.liftoff.BookApp.controllers;

import org.liftoff.BookApp.data.BookOwnerRepository;
import org.liftoff.BookApp.data.BookRepository;
import org.liftoff.BookApp.models.Book;
import org.liftoff.BookApp.models.BookOwner;
import org.liftoff.BookApp.models.User;
import org.liftoff.BookApp.models.dto.BookFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping({"books"})
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookOwnerRepository bookOwnerRepository;

    @Autowired
    AuthenticationController authenticationController;

    public BookController() {
    }

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books/index";
    }

    @GetMapping({"add"})
    public String displayAddBookForm(Model model) {
        model.addAttribute(new BookFormDTO());
        return "books/add";
    }

    @PostMapping({"add"})
    public String processAddBookForm(@ModelAttribute @Valid BookFormDTO bookFormDTO, Errors errors, Model model,
                                     HttpServletRequest request) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Book");
            return "books/add";
        } else {
            HttpSession session = request.getSession();
            User user = authenticationController.getUserFromSession(session);

            Book newBook = new Book(bookFormDTO.getTitle(), bookFormDTO.getAuthor(), bookFormDTO.getGenre());
            Book savedBook = bookRepository.save(newBook);

            BookOwner bookOwner = new BookOwner();
            bookOwner.setBookId(savedBook.getId());
            bookOwner.setUserId(user.getId());
            bookOwnerRepository.save(bookOwner);
            return "redirect:./";
        }
    }

    @GetMapping({"view/{bookId}"})
    public String displayViewBook(Model model, @PathVariable int bookId) {
        Optional optBook = this.bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            Book book = (Book) optBook.get();
            model.addAttribute("book", book);
            return "books/view";
        } else {
            return "redirect:../";
        }
    }

}