package com.marcosmdev.ec.APIRestMain.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * JPA entity that maps to the clientes table.
 */
@Data //Lombook te hace los getters y setters (a veces sí se agradece)
@AllArgsConstructor //loombok te hace el constructor que tenga todos los atributos
@NoArgsConstructor //lombook te genera un constructor vacío
@ToString // loombok te genera el toString()
@Builder
@Entity // base de datos conección
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @Column(name = "id_cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID autoincremental
    private Integer idCliente;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "correo")
    private String correo;
    @Column(name = "fecha_registro")
    private Date fechaRegistro;

}
