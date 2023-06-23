package com.examples.junit5testorder.user.web;

import com.examples.junit5testorder.user.User;
import com.examples.junit5testorder.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository repository;

    @Test
    void test() throws Exception {
        User user = User.builder().name("Wim").id(1L).build();
        Mockito.when(repository.findById(1L))
               .thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{id}", 1L))
               .andExpect(status().isOk());
    }
}
