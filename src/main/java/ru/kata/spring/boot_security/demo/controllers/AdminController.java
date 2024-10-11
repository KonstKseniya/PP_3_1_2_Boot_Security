package ru.kata.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/edit_panel")
    public String getShowAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsersWithRole());
        return "admin/edit_panel";
    }

    @GetMapping("/edit_panel/new_user")
    public String getCreateUserForm(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("users", new User());
        return "admin/new_user";
    }

    @PostMapping("/edit_panel/new_user")
    public String createUser(@ModelAttribute("user") @Valid User user) {
        userService.saveUser(user);
        return "redirect:/admin/edit_panel";
    }

    @PostMapping("/edit_panel/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/edit_panel";
    }

    @GetMapping("/edit_panel/edit_user")
    public String getEditUserForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("users", userService.getUserById(id));
        return "admin/edit_user";
    }

    @PostMapping("/edit_panel/edit_user")
    public String editUser(@ModelAttribute("user") @Valid User user) {
        userService.saveUser(user);
        return "redirect:/admin/edit_panel";
    }
}
