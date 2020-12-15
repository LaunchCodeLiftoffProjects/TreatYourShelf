package org.liftoff.BookApp.controllers;

import org.liftoff.BookApp.data.BookOwnerRepository;
import org.liftoff.BookApp.data.BookRepository;
import org.liftoff.BookApp.data.UserRepository;
import org.liftoff.BookApp.models.User;
import org.liftoff.BookApp.models.dto.UpdateFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookOwnerRepository bookOwnerRepository;


    public HomeController() {
    }

    @RequestMapping({""})
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Treat Your Shelf!");
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        if (user == null) {
            model.addAttribute("loginstatus", "");
        } else {
            model.addAttribute("loginstatus", user.getUsername());
        }
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }

    @GetMapping({"userProfile"})
    public String displayUserProfile(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Profile");
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("communication", user.getCommunication());
        return "userProfile/index";
    }

    @GetMapping("/update")
    public String displayUpdateForm(Model model) {
        model.addAttribute(new UpdateFormDTO());
        model.addAttribute("title", "Update Profile");
        return "update";
    }

    @PostMapping("/update")
    public String processUpdateForm(@ModelAttribute @Valid UpdateFormDTO updateFormDTO, Errors errors, HttpServletRequest request, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Update Profile");
            return "update";
        }
        User existingUser = userRepository.findByUsername(updateFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Update Profile");
            return "update";
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        user.setUsername(updateFormDTO.getUsername());
        user.setCommunication(updateFormDTO.getCommunication());
        userRepository.save(user);

        return "userProfile/index";
    }

}