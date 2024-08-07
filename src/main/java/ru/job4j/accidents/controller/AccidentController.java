package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam List<Integer> rIds) {
        accidentService.add(accident, rIds);
        return "redirect:/";
    }

    @GetMapping("/updateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Нарушение не найдено");
            return "errors/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "/updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam List<Integer> rIds) {
        accidentService.update(accident, rIds);
        return "redirect:/";
    }
}