package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getViewCreateAccidentShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/createAccident"))
                .andDo(print()).andExpect(status().isOk()).andExpect(view().name("createAccident"));
    }
}