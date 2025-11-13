package com.tecsup.petclinic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.controller.OwnerController;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
public class OwnerControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateOwnerWithMock() throws Exception {
        Owner owner = new Owner(1, "Mario", "Lopez", "Av. Colonial 555", "Trujillo", "987777888");

        Mockito.when(ownerService.create(Mockito.any(Owner.class))).thenReturn(owner);

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Mario")))
                .andExpect(jsonPath("$.city", is("Trujillo")));
    }


    @Test
    public void testFindOwnerByIdWithMock() throws Exception {
        Owner owner = new Owner(10, "Pedro", "Salas", "Av. Bolognesi 444", "Lima", "999555444");

        Mockito.when(ownerService.findById(10)).thenReturn(owner);

        mockMvc.perform(get("/owners/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Pedro")))
                .andExpect(jsonPath("$.city", is("Lima")));
    }


    @Test
    public void testUpdateOwnerWithMock() throws Exception {
        Owner existingOwner = new Owner(5, "Luis", "Gomez", "Av. Grau 111", "Cusco", "955111222");
        Owner updatedOwner = new Owner(5, "Luis", "Gomez", "Av. Grau 999", "Cusco", "955111222");

        Mockito.when(ownerService.update(Mockito.any(Owner.class))).thenReturn(updatedOwner);

        mockMvc.perform(put("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOwner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is("Av. Grau 999")));
    }


    @Test
    public void testDeleteOwnerWithMock() throws Exception {
        Integer id = 3;

        Mockito.doNothing().when(ownerService).delete(id);

        mockMvc.perform(delete("/owners/" + id))
                .andExpect(status().isOk());
    }
}
