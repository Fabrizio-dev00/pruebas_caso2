package com.tecsup.petclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateOwner() throws Exception {
        Owner owner = new Owner("Luis", "Torres", "Av. Grau 777", "Lima", "999111222");

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Luis")))
                .andExpect(jsonPath("$.city", is("Lima")));
    }

    @Test
    public void testFindOwnerById() throws Exception {
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", not(emptyString())));
    }

    @Test
    public void testUpdateOwner() throws Exception {
        Owner owner = new Owner("Lucía", "Gomez", "Av. Lima 555", "Cusco", "912345678");
        owner.setId(1);

        mockMvc.perform(put("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", is("Cusco")));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        mockMvc.perform(delete("/owners/1"))
                .andExpect(status().isOk());
    }
}
