package com.marcosmdev.ec.APIRestMain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosmdev.ec.APIRestMain.model.dto.ClienteDTO;
import com.marcosmdev.ec.APIRestMain.model.entity.Cliente;
import com.marcosmdev.ec.APIRestMain.service.IClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IClienteService clienteService;

    @Test
    void showAll_returnsList() throws Exception {
        Cliente c1 = Cliente.builder()
                .idCliente(1)
                .nombre("Ana")
                .apellido("Perez")
                .correo("ana.perez@example.com")
                .fechaRegistro(new Date())
                .build();

        when(clienteService.listAlll()).thenReturn(List.of(c1));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(""))
                .andExpect(jsonPath("$.object[0].idCliente").value(1));
    }

    @Test
    void showById_whenNotFound_returns404() throws Exception {
        when(clienteService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/v1/cliente/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("El registro que intenta buscar, no existe!!"));
    }

    @Test
    void create_returnsCreated() throws Exception {
        ClienteDTO dto = ClienteDTO.builder()
                .nombre("Ana")
                .apellido("Perez")
                .correo("ana.perez@example.com")
                .fechaRegistro(new Date())
                .build();

        Cliente saved = Cliente.builder()
                .idCliente(5)
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .correo(dto.getCorreo())
                .fechaRegistro(dto.getFechaRegistro())
                .build();

        when(clienteService.save(any(ClienteDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Guardado correctamente"))
                .andExpect(jsonPath("$.object.idCliente").value(5));
    }

    @Test
    void update_whenNotFound_returns404() throws Exception {
        ClienteDTO dto = ClienteDTO.builder()
                .nombre("Ana")
                .apellido("Perez")
                .correo("ana.perez@example.com")
                .fechaRegistro(new Date())
                .build();

        when(clienteService.existsById(1)).thenReturn(false);

        mockMvc.perform(put("/api/v1/cliente/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("El registro que intenta actualizar no se encuentra en la base de datos."));
    }
}
