package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

@Controller
@AllArgsConstructor
public class RegControl {

    private final UserService userService;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        var savedUser = userService.register(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("error", "Пользователь уже существует! Зарегистрируйтесь повторно!");
            return "/reg";
        }
        model.addAttribute("message", "Пользователь зарегистрирован");
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}