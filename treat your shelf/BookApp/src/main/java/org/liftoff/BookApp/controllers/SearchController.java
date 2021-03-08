package org.liftoff.BookApp.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import org.liftoff.BookApp.data.BookRepository;
import org.liftoff.BookApp.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"search"})
public class SearchController {
    @Autowired
    private BookRepository bookRepository;

    public SearchController() {
    }

    @GetMapping({""})
    public String displaySearchedBooks(@RequestParam String search, Model model) {
        ArrayList<Book> searchedBooks = new ArrayList();
        Iterable<Book> allBooks = this.bookRepository.findAll();
        Iterator var5 = allBooks.iterator();

        while(true) {
            Book book;
            String titles;
            String author;
            String loweredTitles;
            do {
                if (!var5.hasNext()) {
                    if (search.equals("all")) {
                        model.addAttribute("books", this.bookRepository.findAll());
                    }

                    return "search";
                }

                book = (Book)var5.next();
                search = search.toLowerCase().replaceAll("\\s", "");
                titles = book.getTitle();
                author = book.getAuthor().toLowerCase().replaceAll("\\s", "");
                loweredTitles = titles.toLowerCase().replaceAll("\\s", "");
            } while(!search.equals(loweredTitles) && !titles.contains(search) && !author.contains(search));

            searchedBooks.add(book);
            model.addAttribute("books", searchedBooks);
        }
    }
}