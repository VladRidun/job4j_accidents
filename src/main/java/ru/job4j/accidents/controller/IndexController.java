package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.MemoryAccidentService;

@Controller
public class IndexController {
    private final MemoryAccidentService memoryAccidentService;

    public IndexController(MemoryAccidentService memoryAccidentService) {
        this.memoryAccidentService = memoryAccidentService;
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("user", "Vlad Ridun");
        model.addAttribute("accidents", memoryAccidentService.findAll().get());
        return "index";
    }
}