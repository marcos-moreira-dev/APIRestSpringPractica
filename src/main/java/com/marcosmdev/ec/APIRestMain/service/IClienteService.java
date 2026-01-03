package com.marcosmdev.ec.APIRestMain.service;
import com.marcosmdev.ec.APIRestMain.model.dto.ClienteDTO;
import com.marcosmdev.ec.APIRestMain.model.entity.Cliente;

import java.util.List;

/**
 * Service contract for client operations.
 */
public interface IClienteService {

    /**
     * Lists all clients.
     *
     * @return list of clients
     */
    List<Cliente> listAlll();

    /**
     * Saves or updates a client from the provided DTO.
     *
     * @param cliente client DTO data
     * @return persisted client entity
     */
    Cliente save(ClienteDTO cliente);

    /**
     * Finds a client by id.
     *
     * @param id client identifier
     * @return client entity or null
     */
    Cliente findById(Integer id);

    /**
     * Deletes the given client.
     *
     * @param cliente client entity to delete
     */
    void delete(Cliente cliente);

    /**
     * Checks if a client exists by id.
     *
     * @param id client identifier
     * @return true if exists
     */
    boolean existsById(Integer id);

}
