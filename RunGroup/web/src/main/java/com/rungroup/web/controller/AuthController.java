package com.rungroup.web.controller;

import com.rungroup.web.dto.RegistrationDto;
import com.rungroup.web.models.UserEntity;
import com.rungroup.web.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final AuthenticationService userService;

    public AuthController(AuthenticationService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result, Model model) {

        UserEntity existingUsersEmail = userService.findByEmail(user.getEmail());
        if(existingUsersEmail != null && existingUsersEmail.getEmail() != null && !existingUsersEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }

        UserEntity existingUsername = userService.findByUsername(user.getUsername());
        if(existingUsername != null && existingUsername.getUsername() != null && !existingUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }

        if(result.hasErrors()) {
            model.addAttribute("user" , user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
