package com.marcosmdev.ec.APIRestMain.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for transferring client data through the API.
 */
@Data //Lombook te hace los getters y setters (a veces sí se agradece)
@AllArgsConstructor //loombok te hace el constructor que tenga todos los atributos
@NoArgsConstructor //lombook te genera un constructor vacío
@ToString // loombok te genera el toString()
@Builder
public class ClienteDTO implements Serializable {

    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaRegistro;

}
