package ru.job4j.accidents.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void whenGetRegPageThenReg() throws Exception {
        mockMvc.perform(get("/reg"))
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    void whenPostRegThenGetIndexPage() throws Exception {
        User user = new User(0, "user", "pass", new Authority(), true);
        when(userService.register(any(User.class))).thenReturn(Optional.of(user));
        mockMvc.perform(post("/reg")
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).register(userCaptor.capture());
        var captorValue = userCaptor.getValue();
        assertThat(captorValue.getUsername()).isEqualTo("user");
        assertThat(captorValue.getPassword()).isEqualTo("pass");
    }

    @Test
    void whenPostRegSaveThenGetRegPage() throws Exception {
        User user = new User(0, "user", "pass", new Authority(), true);
        when(userService.register(any(User.class))).thenReturn(Optional.empty());
        mockMvc.perform(post("/reg")
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("/reg"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void whenLoginShouldReturnStatus200() throws Exception {
        mockMvc.perform(get("/login")).
                andDo(print()).andExpect(status().isOk()).andExpect(view().name("login"));
    }

    @Test
    void whenLogoutShouldReturnStatus302() throws Exception {
        mockMvc.perform(get("/logout")).
                andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?logout=true"));
    }
}