package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
