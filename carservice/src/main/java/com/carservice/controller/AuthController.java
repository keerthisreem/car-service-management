package com.carservice.controller;

import com.carservice.entity.User;
import com.carservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                             @RequestParam(required = false) String logout,
                             Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password!");
        if (logout != null) model.addAttribute("message", "Logged out successfully.");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, RedirectAttributes attrs) {
        try {
            userService.registerUser(user);
            attrs.addFlashAttribute("message", "Registration successful! Please login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/register";
        }
    }
}
