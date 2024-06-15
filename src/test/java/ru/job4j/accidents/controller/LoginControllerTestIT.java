package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class LoginControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenLoginShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/login")).
                andDo(print()).andExpect(status().isOk()).andExpect(view().name("login"));
    }

    @Test
    void whenLogoutShouldReturnStatus302() throws Exception {
        mockMvc.perform(get("/logout")).
                andDo(print()).andExpect(status().is3xxRedirection());
    }
}