# APIRestMain - Documentacion del proyecto

## Vision general
Proyecto Spring Boot para una API REST sencilla de gestion de clientes. Expone endpoints CRUD, usa JPA con MySQL y aplica una arquitectura en capas con controlador, servicio y acceso a datos. Incluye DTOs y un payload estandar para respuestas.

## Tecnologias y dependencias
- Java 17
- Spring Boot 3.5.9
- spring-boot-starter-web (API REST)
- spring-boot-starter-data-jpa (persistencia)
- MySQL Connector/J (base de datos)
- Lombok (reduccion de boilerplate)
- Spring Boot Test (tests)

## Arquitectura y patrones
- Arquitectura en capas:
  - Controller: recibe solicitudes HTTP y arma respuestas.
  - Service: contiene logica de negocio y orquesta persistencia.
  - DAO/Repository: acceso a datos via Spring Data.
  - Entity: modelo persistente JPA.
  - DTO: modelo de transferencia expuesto a la API.
- Patrones/estilos:
  - DAO/Repository pattern con CrudRepository.
  - DTO pattern para desacoplar entidad de la API.
  - Builder pattern (Lombok) para crear objetos.
  - Dependency Injection (Spring) para wiring de componentes.
  - Transaction Script (metodos del servicio con @Transactional).

## Componentes construidos
- Aplicacion principal:
  - `src/main/java/com/marcosmdev/ec/APIRestMain/ApiRestMainApplication.java`
- Controlador REST:
  - `src/main/java/com/marcosmdev/ec/APIRestMain/controller/ClienteController.java`
- Servicio:
  - `src/main/java/com/marcosmdev/ec/APIRestMain/service/IClienteService.java`
  - `src/main/java/com/marcosmdev/ec/APIRestMain/service/impl/ClienteImplService.java`
- Acceso a datos:
  - `src/main/java/com/marcosmdev/ec/APIRestMain/model/dao/ClienteDAO.java`
- Modelo:
  - Entidad: `src/main/java/com/marcosmdev/ec/APIRestMain/model/entity/Cliente.java`
  - DTO: `src/main/java/com/marcosmdev/ec/APIRestMain/model/dto/ClienteDTO.java`
  - Payload de respuesta: `src/main/java/com/marcosmdev/ec/APIRestMain/model/payload/MensajeResponse.java`
- Configuracion:
  - `src/main/resources/application.properties`
  - `src/main/resources/sql/schema.sql`
  - `src/main/resources/sql/data.sql`
- Test basico:
  - `src/test/java/com/marcosmdev/ec/APIRestMain/ApiRestMainApplicationTests.java`

## Estructuras de datos aplicadas
### 1) `List<Cliente>`
**Que es:** una lista es una coleccion ordenada de elementos. Imagina una fila donde cada elemento tiene una posicion (indice) que empieza en 0. En este proyecto, la lista contiene objetos `Cliente`.

**Sintaxis basica en Java:**
```java
List<Cliente> clientes = new ArrayList<>();
```
- `List` es una interfaz (un contrato).
- `ArrayList` es una implementacion concreta (la mas comun).
- Los simbolos `<Cliente>` indican el tipo de elementos (generics).

**Caracteristicas clave:**
- **Ordenada:** mantiene el orden en que se insertan los elementos.
- **Indices:** puedes acceder con `get(0)`, `get(1)`, etc.
- **Permite duplicados:** puedes tener dos clientes iguales segun `equals`.
- **Tamanio dinamico:** crece o se reduce automaticamente.

**Uso en el proyecto:**
- Se usa para devolver todos los clientes desde el servicio:
  - El DAO devuelve un conjunto iterable y se convierte a `List`.
- El controlador expone ese listado en el endpoint `GET /clientes`.

### 2) `Map<String, Object>`
**Que es:** un mapa es una estructura de pares **clave -> valor**. La clave es unica, y se usa para recuperar el valor.

**Sintaxis basica en Java:**
```java
Map<String, Object> datos = new HashMap<>();
datos.put("mensaje", "ok");
datos.put("cliente", cliente);
```
- `Map` es la interfaz.
- `HashMap` es la implementacion mas usada.
- `String` es el tipo de clave.
- `Object` permite almacenar valores de distintos tipos.

**Caracteristicas clave:**
- **No es una lista:** no hay indices numericos.
- **Claves unicas:** si usas la misma clave, reemplazas el valor.
- **Acceso rapido:** se busca por clave, no por posicion.
- **Sin orden garantizado** (en `HashMap`): el orden de insercion no se conserva.

**Uso en el proyecto:**
- Se puede usar para construir respuestas con multiples campos
  (aunque en este proyecto la respuesta principal se da con `MensajeResponse`).

### 3) `ResponseEntity<?>`
**Que es:** no es una estructura de datos clasica como lista o mapa, sino un **contenedor** que representa la respuesta HTTP completa.

**Sintaxis basica en Java:**
```java
ResponseEntity<MensajeResponse> resp =
    new ResponseEntity<>(mensajeResponse, HttpStatus.OK);
```
- `ResponseEntity` encapsula **cuerpo** + **codigo HTTP** + **headers**.
- El `<?>` significa "tipo generico desconocido" cuando no se quiere fijar uno.

**Caracteristicas clave:**
- **Control total del HTTP:** puedes elegir el status (`200`, `201`, `404`, etc.).
- **Cuerpo flexible:** cualquier objeto serializable a JSON.
- **Headers opcionales:** tambien puedes agregar headers personalizados.

**Uso en el proyecto:**
- Todos los endpoints retornan `ResponseEntity<?>` para controlar el status.

### 4) `Optional<Cliente>` (interno de JPA)
**Que es:** `Optional` es un contenedor que puede tener **un valor** o **estar vacio**. Se usa para evitar `null` de forma explicita.

**Sintaxis basica en Java:**
```java
Optional<Cliente> opt = clienteDao.findById(id);
Cliente c = opt.orElse(null);
```
- `Optional<Cliente>` indica "puede venir un Cliente o no".
- Metodos comunes: `isPresent()`, `orElse(...)`, `orElseThrow(...)`.

**Caracteristicas clave:**
- **Explicito:** obliga a pensar el caso "no existe".
- **Evita NullPointerException** si se usa bien.
- **No es coleccion:** solo 0 o 1 valor.

**Uso en el proyecto:**
- `ClienteDAO.findById` devuelve `Optional<Cliente>`.
- El servicio lo transforma a `Cliente` o `null`.

### 5) `Serializable`
**Que es:** es una interfaz de "marcado". No tiene metodos, pero indica que una clase **se puede convertir a bytes** (serializar).

**Sintaxis basica en Java:**
```java
public class Cliente implements Serializable {
    // ...
}
```

**Caracteristicas clave:**
- **Permite serializar** objetos (guardar, enviar, cachear).
- **No requiere metodos**, solo implementar la interfaz.
- **Uso comun en frameworks** que necesitan convertir objetos en JSON o transferirlos.

**Uso en el proyecto:**
- `Cliente`, `ClienteDTO` y `MensajeResponse` implementan `Serializable`
  para permitir su uso en respuestas y en posibles procesos de serializacion.

## Flujo principal de la API
1. El cliente invoca un endpoint en `ClienteController`.
2. El controlador llama a `IClienteService`.
3. El servicio transforma DTO <-> Entity y usa `ClienteDAO`.
4. `ClienteDAO` delega en Spring Data JPA para persistencia.
5. La respuesta se envuelve en `MensajeResponse` y `ResponseEntity`.

## Endpoints principales
Base path: `/api/v1`
- `GET /clientes` lista todos los clientes.
- `GET /cliente/{id}` obtiene un cliente por id.
- `POST /cliente` crea un cliente.
- `PUT /cliente/{id}` actualiza un cliente.
- `DELETE /cliente/{id}` elimina un cliente.

### Ejemplos de uso (Postman)
Base URL: `http://localhost:8080/api/v1`

**GET /clientes**
```http
GET http://localhost:8080/api/v1/clientes
Accept: application/json
```
Respuesta esperada (200):
```json
{
  "message": "",
  "object": [
    {
      "idCliente": 1,
      "nombre": "Joel",
      "apellido": "Jurado",
      "correo": "juradoec@yahoo.com",
      "fechaRegistro": "2023-08-01"
    },
    {
      "idCliente": 2,
      "nombre": "Carlos",
      "apellido": "Miranda",
      "correo": "mirandaTr98@gmail.com",
      "fechaRegistro": "2023-08-02"
    }
  ]
}
```

**GET /cliente/{id}**
```http
GET http://localhost:8080/api/v1/cliente/1
Accept: application/json
```
Respuesta esperada (200):
```json
{
  "message": "",
  "object": {
    "idCliente": 1,
    "nombre": "Joel",
    "apellido": "Jurado",
    "correo": "juradoec@yahoo.com",
    "fechaRegistro": "2023-08-01"
  }
}
```

**POST /cliente**
```http
POST http://localhost:8080/api/v1/cliente
Content-Type: application/json
Accept: application/json

{
  "nombre": "Ana",
  "apellido": "Perez",
  "correo": "ana.perez@example.com",
  "fechaRegistro": "2024-01-10"
}
```
Respuesta esperada (201):
```json
{
  "message": "Guardado correctamente",
  "object": {
    "idCliente": 5,
    "nombre": "Ana",
    "apellido": "Perez",
    "correo": "ana.perez@example.com",
    "fechaRegistro": "2024-01-10"
  }
}
```

**PUT /cliente/{id}**
```http
PUT http://localhost:8080/api/v1/cliente/1
Content-Type: application/json
Accept: application/json

{
  "nombre": "Ana",
  "apellido": "Perez",
  "correo": "ana.perez@example.com",
  "fechaRegistro": "2024-01-11"
}
```
Respuesta esperada (201):
```json
{
  "message": "Guardado correctamente",
  "object": {
    "idCliente": 1,
    "nombre": "Ana",
    "apellido": "Perez",
    "correo": "ana.perez@example.com",
    "fechaRegistro": "2024-01-11"
  }
}
```

**DELETE /cliente/{id}**
```http
DELETE http://localhost:8080/api/v1/cliente/1
Accept: application/json
```
Respuesta esperada segun el codigo (204):
```json
{
  "idCliente": 1,
  "nombre": "Ana",
  "apellido": "Perez",
  "correo": "ana.perez@example.com",
  "fechaRegistro": "2024-01-11"
}
```
Nota: el controlador retorna `HttpStatus.NO_CONTENT` (204) pero incluye un cuerpo con el cliente eliminado. En HTTP, 204 normalmente no lleva contenido.

## Rol de cada anotacion
### Spring Boot / Web
- `@SpringBootApplication` (ApiRestMainApplication): habilita auto-configuracion, escaneo de componentes y configuracion de Spring Boot.
- `@RestController` (ClienteController): marca la clase como controlador REST y serializa respuestas a JSON.
- `@RequestMapping("/api/v1")` (ClienteController): prefijo comun para las rutas.
- `@GetMapping` (ClienteController): mapea HTTP GET a metodos.
- `@PostMapping` (ClienteController): mapea HTTP POST a metodos.
- `@PutMapping` (ClienteController): mapea HTTP PUT a metodos.
- `@DeleteMapping` (ClienteController): mapea HTTP DELETE a metodos.
- `@RequestBody` (ClienteController): convierte el body JSON a DTO.
- `@PathVariable` (ClienteController): toma variables de la URL.
- `@Autowired` (ClienteController, ClienteImplService): inyeccion de dependencias.
- `@Service` (ClienteImplService): registra el servicio como bean de Spring.
- `@Transactional` (ClienteImplService): define el limite transaccional para operaciones de escritura.
- `@Transactional(readOnly = true)` (ClienteImplService): transaccion de solo lectura para consultas.

### JPA / Persistencia
- `@Entity` (Cliente): marca la clase como entidad JPA.
- `@Table(name = "clientes")` (Cliente): mapea la entidad a la tabla.
- `@Id` (Cliente): marca la PK.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` (Cliente): PK autoincremental en la BD.
- `@Column(name = "...")` (Cliente): mapea campos a columnas.

### Lombok
- `@Data` (Cliente, ClienteDTO, MensajeResponse): genera getters, setters, equals, hashCode y toString.
- `@AllArgsConstructor` (Cliente, ClienteDTO): genera constructor con todos los campos.
- `@NoArgsConstructor` (Cliente, ClienteDTO): genera constructor vacio.
- `@ToString` (Cliente, ClienteDTO, MensajeResponse): genera toString.
- `@Builder` (Cliente, ClienteDTO, MensajeResponse): habilita el patron builder.

### Testing
- `@SpringBootTest` (ApiRestMainApplicationTests): carga el contexto de Spring para pruebas.

## Base de datos
- Base: `db_springboot_dev`
- Tabla: `clientes`
- Script de esquema: `src/main/resources/sql/schema.sql`
- Datos iniciales: `src/main/resources/sql/data.sql`
- Configuracion DB: `src/main/resources/application.properties`

## Observaciones de configuracion
- `spring.jpa.hibernate.ddl-auto=none` desactiva la creacion automatica de tablas.
- `spring.sql.init.mode=never` desactiva ejecucion automatica de scripts SQL.
- `spring.datasource.password` se lee desde variable de entorno `DB_PASSWORD` definida en `.env`.

## Siguientes pasos recomendados
1) Completar documentacion tecnica
   - Agregar ejemplos de errores por endpoint (404, 405) con su JSON real.
   - Documentar codigos HTTP esperados y condiciones de cada respuesta.
   - Mantener `DOCUMENTACION_PROYECTO.md` como fuente unica para el proyecto.

2) Fortalecer Swagger/OpenAPI
   - Incluir ejemplos de request/response con `@ExampleObject`.
   - Agregar anotaciones `@Schema` en `ClienteDTO`, `Cliente` y `MensajeResponse` para mejorar la descripcion de campos.
   - Validar que `swagger-ui-custom.html` refleje lo que se expone en la API.

3) Consolidar pruebas rapidas
   - Ampliar `@WebMvcTest` con casos positivos y negativos por endpoint.
   - Verificar respuestas de error consistentes (mensaje y estructura).
   - Agregar pruebas unitarias del servicio para reglas de negocio.

4) Estabilizar persistencia y datos
   - Definir si los scripts SQL se ejecutan en dev (ajustar `spring.sql.init.mode`).
   - Normalizar datos de prueba y versionar `schema.sql`/`data.sql`.
   - Confirmar que el mapeo JPA coincide con la tabla real.

5) Mejorar validaciones de entrada
   - Agregar validaciones en DTO con `jakarta.validation` (`@NotBlank`, `@Email`, etc.).
   - Manejar errores de validacion con respuestas estandarizadas.
   - Documentar reglas de validacion en Swagger.

6) Preparar despliegue local y reproducible
   - Definir variables de entorno requeridas en un README corto.
   - Si usas Docker, agregar `docker-compose.yml` para MySQL local.
   - Agregar un script de arranque o instrucciones claras para nuevos miembros.
