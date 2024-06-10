package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
public class IndexController {
    private final AccidentService accidentService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}