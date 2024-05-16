package ru.job4j.accidents.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/create")
    public String viewCreateAccident() {

        return "create";
    }

    @GetMapping
    public String save(@ModelAttribute Accident accident) {
        accidentService.add(accident);
        return "redirect:/index";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Accident accident) {
        accidentService.add(accident);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
        }
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/index";
    }
}
