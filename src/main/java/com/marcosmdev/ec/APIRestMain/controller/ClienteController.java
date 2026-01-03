package com.marcosmdev.ec.APIRestMain.controller;

import com.marcosmdev.ec.APIRestMain.model.dto.ClienteDTO;
import com.marcosmdev.ec.APIRestMain.model.entity.Cliente;
import com.marcosmdev.ec.APIRestMain.model.payload.MensajeResponse;
import com.marcosmdev.ec.APIRestMain.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for client CRUD operations.
 */
@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    /**
     * Returns all registered clients.
     *
     * @return response with list of clients or empty message
     */
    @Operation(
            summary = "List all clients",
            description = "Returns the complete list of clients registered in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List returned",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            )
    })
    @GetMapping("clientes")
    public ResponseEntity<?> showAll() {
        List<Cliente> getList = clienteService.listAlll();
        if (getList == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .message("No hay registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .message("")
                        .object(getList)
                        .build()
                , HttpStatus.OK);
    }

    /**
     * Creates a new client from the provided DTO.
     *
     * @param clienteDto client data
     * @return response with created client data
     */
    @Operation(
            summary = "Create client",
            description = "Creates a new client with the provided data."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Client created",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "405",
                    description = "Invalid data or persistence error",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            )
    })
    @PostMapping("cliente")
    public ResponseEntity<?> create(
            @RequestBody(
                    description = "Client payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))
            )
            @org.springframework.web.bind.annotation.RequestBody ClienteDTO clienteDto
    ) {
        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .message("Guardado correctamente")
                    .object(ClienteDTO.builder()
                            .idCliente(clienteSave.getIdCliente())
                            .nombre(clienteSave.getNombre() )
                            .apellido(clienteSave.getApellido())
                            .correo(clienteSave.getCorreo())
                            .fechaRegistro(clienteSave.getFechaRegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Updates an existing client by id.
     *
     * @param clienteDto updated client data
     * @param id         client identifier
     * @return response with updated client data or not found message
     */
    @Operation(
            summary = "Update client",
            description = "Updates an existing client by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Client updated",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "405",
                    description = "Invalid data or persistence error",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            )
    })
    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(
            @RequestBody(
                    description = "Client payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteDTO.class))
            )
            @org.springframework.web.bind.annotation.RequestBody ClienteDTO clienteDto,
            @Parameter(description = "Client id", required = true)
            @PathVariable Integer id
    ) {
        Cliente clienteUpdate = null;
        try {
            if (clienteService.existsById(id)) {
                clienteDto.setIdCliente(id);
                clienteUpdate = clienteService.save(clienteDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .message("Guardado correctamente")
                        .object(ClienteDTO.builder()
                                .idCliente(clienteUpdate.getIdCliente())
                                .nombre(clienteUpdate.getNombre())
                                .apellido(clienteUpdate.getApellido())
                                .correo(clienteUpdate.getCorreo())
                                .fechaRegistro(clienteUpdate.getFechaRegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .message("El registro que intenta actualizar no se encuentra en la base de datos.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Deletes a client by id.
     *
     * @param id client identifier
     * @return response with deleted client or error message
     */
    @Operation(
            summary = "Delete client",
            description = "Deletes a client by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Client deleted (body returned by current implementation)",
                    content = @Content(schema = @Schema(implementation = Cliente.class))
            ),
            @ApiResponse(
                    responseCode = "405",
                    description = "Persistence error",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            )
    })
    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Client id", required = true)
            @PathVariable Integer id
    ) {
        try {
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .message(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Returns a single client by id.
     *
     * @param id client identifier
     * @return response with client data or not found message
     */
    @Operation(
            summary = "Get client by id",
            description = "Returns a single client by its id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Client returned",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found",
                    content = @Content(schema = @Schema(implementation = MensajeResponse.class))
            )
    })
    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(
            @Parameter(description = "Client id", required = true)
            @PathVariable Integer id
    ) {
        Cliente cliente = clienteService.findById(id);

        if (cliente == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .message("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .message("")
                        .object(ClienteDTO.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .correo(cliente.getCorreo())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

}
