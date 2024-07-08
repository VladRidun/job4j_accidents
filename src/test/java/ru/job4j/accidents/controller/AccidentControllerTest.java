package ru.job4j.accidents.controller;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.dto.AccidentReadDto;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @MockBean
    private AccidentTypeService accidentTypeService;

    @MockBean
    private RuleService ruleService;

    @Test
    @WithMockUser
    void getViewCreateAccidentShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/createAccident"))
                .andDo(print()).andExpect(status().isOk()).andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    void whenGetCreationPageThenAccidentsCreate() throws Exception {
        when(accidentTypeService.findAll()).thenReturn(List.of());
        when(ruleService.findAll()).thenReturn(Set.of());
        mockMvc.perform(get("/createAccident"))
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"))
                .andExpect(model().attributeExists("types", "rules"));
    }

    @Test
    @WithMockUser
    void whenGetFormUpdateAccidentThenAccidentsEdit() throws Exception {
        AccidentType accidentType = new AccidentType(1, "test");
        Rule rule = new Rule(1, "test");
        AccidentReadDto accidentDto = new AccidentReadDto(1, "test", "test", "test", accidentType.toString(), rule.toString());

        when(accidentService.findById(any(Integer.class))).thenReturn(Optional.of(accidentDto));
        mockMvc.perform(get("/updateAccident").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/updateAccident"))
                .andExpect(model().attributeExists("accident", "types", "rules"))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void whenGetFormUpdateAccidentThenError() throws Exception {
        when(accidentService.findById(any(Integer.class))).thenReturn(Optional.empty());
        mockMvc.perform(get("/updateAccident").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    @WithMockUser
    void whenPostCreateThenGetAccidents() throws Exception {
        AccidentType accidentType = new AccidentType(4, "Type1");
        Rule rule = new Rule(5, "Rule1");
        Accident accident = new Accident(
                0, "nameTest", "textTest", "addressTest", accidentType, Set.of(rule));

        when(accidentService.add(any(Accident.class), eq(List.of(rule.getId())))).thenReturn(accident);
        mockMvc.perform(post("/saveAccident")
                        .param("name", accident.getName())
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rIds", String.valueOf(accident.getRules().iterator().next().getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Accident> accidentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<List<Integer>> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(accidentService).add(accidentCaptor.capture(), listCaptor.capture());
        var captorValue = accidentCaptor.getValue();
        assertThat(captorValue.getName()).isEqualTo("nameTest");
        assertThat(captorValue.getAddress()).isEqualTo("addressTest");
        assertThat(captorValue.getType().getId()).isEqualTo(4);
        assertThat(listCaptor.getValue()).isEqualTo(List.of(5));
    }

    @Test
    @WithMockUser
    void whenPostUpdateAndGetRedirection() throws Exception {
        AccidentType accidentType = new AccidentType(1, "Type1");
        Rule rule = new Rule(1, "Rule1");
        Accident accident = new Accident(
                6, "nameTest", "textTest", "addressTest", accidentType, Set.of(rule));

        mockMvc.perform(post("/updateAccident")
                        .param("name", accident.getName())
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rIds", String.valueOf(accident.getRules().iterator().next().getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}