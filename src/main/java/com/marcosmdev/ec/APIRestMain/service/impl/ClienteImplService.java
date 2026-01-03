package com.marcosmdev.ec.APIRestMain.service.impl;


import com.marcosmdev.ec.APIRestMain.model.dto.ClienteDTO;
import com.marcosmdev.ec.APIRestMain.model.dao.ClienteDAO;
import com.marcosmdev.ec.APIRestMain.model.dto.ClienteDTO;
import com.marcosmdev.ec.APIRestMain.model.entity.Cliente;
import com.marcosmdev.ec.APIRestMain.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for client operations.
 */
@Service
public class ClienteImplService implements IClienteService {

    @Autowired
    private ClienteDAO clienteDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cliente> listAlll() {
        return (List) clienteDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Cliente save(ClienteDTO clienteDto) {
        Cliente cliente = Cliente.builder()
                .idCliente(clienteDto.getIdCliente())
                .nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .correo(clienteDto.getCorreo())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();
        return clienteDao.save(cliente);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Integer id) {
        return clienteDao.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsById(Integer id) {
        return clienteDao.existsById(id);
    }
}
