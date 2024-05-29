package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.JdbcAccidentService;

@Controller
@AllArgsConstructor
public class IndexController {
    private final JdbcAccidentService accidentService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("user", "Vlad Ridun");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}