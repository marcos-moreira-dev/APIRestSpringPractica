# Tests backend y roadmap

## Qué son las pruebas en backend
Las pruebas en backend validan que la lógica, los endpoints y la persistencia funcionen como se espera. Evitan regresiones, documentan el comportamiento y te dan confianza para refactorizar.

## Tipos de pruebas que debes conocer
- Unitarias: prueban lógica aislada (sin Spring, sin BD). Se usan para reglas de negocio, validaciones y mappers.
- Slice tests:
  - Web (`@WebMvcTest`): prueba controladores con `MockMvc` y mocks del servicio.
  - JPA (`@DataJpaTest`): prueba repositorios con BD embebida o contenedores.
- Integración (`@SpringBootTest`): prueba el flujo completo (controller -> service -> DAO).
- Contrato/API: valida que el JSON y los endpoints no cambien de forma inesperada.

## Qué se espera de ti ahora (nivel actual)
- Entender y crear pruebas unitarias con JUnit 5.
- Manejar `@WebMvcTest` con `MockMvc`.
- Mockear dependencias con Mockito (`@MockBean`, `when`, `thenReturn`).
- Saber leer la respuesta JSON y validar campos con `jsonPath`.
- Cubrir happy path y errores comunes (404, 400/405, etc.).

## Roadmap recomendado (paso a paso)
1) Base
   - JUnit 5: `@Test`, aserciones, lifecycle.
   - Mockito: `@Mock`, `@InjectMocks`, `@MockBean`.
2) Web layer
   - `@WebMvcTest`, `MockMvc`, `jsonPath`, `ObjectMapper`.
   - Validaciones de status, body y headers.
3) Service layer
   - Pruebas unitarias con mocks del DAO.
   - Casos borde (null, id inexistente, valores inválidos).
4) Data layer
   - `@DataJpaTest` con H2 o Testcontainers.
   - Validar queries y mapping.
5) Integración
   - `@SpringBootTest` + Testcontainers (MySQL).
   - Pruebas end-to-end con datos reales.
6) Mejora continua
   - Cobertura de errores.
   - Refactor seguro basado en pruebas.

## Temas que deberías dominar ya
- JUnit 5 y assertions.
- Mockito (stubs y verificación básica).
- JSON y `jsonPath` en pruebas de API.
- HTTP status codes (200, 201, 204, 400, 404, 409, 500).
- Buenas prácticas: Arrange-Act-Assert, pruebas pequeñas y legibles.

## Ejemplo mínimo para este proyecto
- WebMvcTest para endpoints del controlador.
- Unit tests de servicio para `save`, `findById` y `existsById`.
- 1 prueba de integración con BD (opcional, si quieres robustez).
