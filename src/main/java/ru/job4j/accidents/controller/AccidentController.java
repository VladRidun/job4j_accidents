package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.MemoryAccidentService;
import ru.job4j.accidents.service.MemoryAccidentTypeService;
import ru.job4j.accidents.service.MemoryRuleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final MemoryAccidentService memoryAccidentService;
    private final MemoryAccidentTypeService memoryAccidentTypeService;
    private final MemoryRuleService memoryRuleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", memoryAccidentTypeService.findAll());
        model.addAttribute("rules", memoryRuleService.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        var inIds = Arrays.stream(ids).map(Integer::parseInt).toList();
        var rules = inIds.stream().map(id -> memoryRuleService.findById(id).get()).collect(Collectors.toSet());
        accident.setRules(rules);
        memoryAccidentService.add(accident);
        return "redirect:/";
    }

    @GetMapping("/updateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        var accidentOptional = memoryAccidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Нарушение не найдено");
            return "errors/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "/updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, Model model) {
        model.addAttribute("message", "Не удалось выполнить редактирование");
        return memoryAccidentService.update(accident) ? "redirect:/" : "errors/404";
    }
}
