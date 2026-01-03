package com.marcosmdev.ec.APIRestMain.model.dao;

import com.marcosmdev.ec.APIRestMain.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 * DAO interface for CRUD operations on Cliente entities.
 */
public interface ClienteDAO extends CrudRepository<Cliente,Integer> {
}
