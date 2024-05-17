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
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final MemoryAccidentService memoryAccidentService;
    private final MemoryAccidentTypeService memoryAccidentTypeService;
    private final MemoryRuleService memoryRuleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", memoryAccidentTypeService.findAll().get());
        model.addAttribute("rules", memoryRuleService.findAll().get());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        AccidentType accidentType = memoryAccidentTypeService.findById(accident.getType().getId()).get();
        String[] ids = req.getParameterValues("rIds");
        accident.setType(accidentType);
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
