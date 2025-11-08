package com.maglebov.MvcBoot.controller;

import com.maglebov.MvcBoot.model.User;
import com.maglebov.MvcBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String create(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "addUser";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "editUser";
        }
        user.setId(id);
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String delete(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}