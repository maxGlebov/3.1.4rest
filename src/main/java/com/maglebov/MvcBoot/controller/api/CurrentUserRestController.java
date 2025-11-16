// src/main/java/com/maglebov/MvcBoot/controller/api/CurrentUserRestController.java

package com.maglebov.MvcBoot.controller.api;

import com.maglebov.MvcBoot.model.User;
import com.maglebov.MvcBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrentUserRestController {

    private final UserService userService;

    @Autowired
    public CurrentUserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.findByEmail(email);
    }
}