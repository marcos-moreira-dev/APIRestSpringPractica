# APIRestMain – Guía rápida de puesta en marcha

Este proyecto es una **API REST con Spring Boot** conectada a **MySQL**, configurada con buenas prácticas básicas: sin contraseñas hardcodeadas, SQL versionado solo como referencia y arranque controlado.

---

## 1) Requisitos
- Java (JDK compatible con el `pom.xml`)
- Maven
- MySQL Server 8.x
- MySQL Workbench (opcional, recomendado)

---

## 2) Estructura relevante del proyecto
```
APIRestMain/
├─ src/main/resources/
│  ├─ application.properties
│  └─ sql/
│     ├─ schema.sql   # Estructura de BD (referencia)
│     └─ data.sql     # Datos de ejemplo (referencia)
├─ .env               # Variables de entorno (NO se sube)
├─ .gitignore
├─ pom.xml
└─ README.md
```

> **Nota:** `schema.sql` y `data.sql` **NO se ejecutan automáticamente**. Se guardan como documentación y referencia.

---

## 3) Base de datos (MySQL)

### Crear la base y la tabla (manual, una sola vez)
Ejecutar en **MySQL Workbench**:
```sql
CREATE DATABASE IF NOT EXISTS db_springboot_dev;
USE db_springboot_dev;

CREATE TABLE IF NOT EXISTS clientes (
  id_cliente INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(45) NOT NULL,
  apellido VARCHAR(45) NOT NULL,
  correo VARCHAR(45) NOT NULL,
  fecha_registro DATE NOT NULL,
  PRIMARY KEY (id_cliente)
);

INSERT INTO clientes (nombre, apellido, correo, fecha_registro) VALUES
('Joel', 'Jurado', 'juradoec@yahoo.com', '2023-08-01'),
('Carlos', 'Miranda', 'mirandaTr98@gmail.com', '2023-08-02'),
('Marcela', 'Sanchez', 'schMarce@itb.com', '2023-08-03'),
('Ben', 'Tennyson', 'ben10@cn.com', '2023-08-04');
```

---

## 4) Configuración segura (sin subir contraseñas)

### `application.properties`
```properties
spring.application.name=APIRestMain
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/db_springboot_dev?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

spring.sql.init.mode=never
```

### Variable de entorno (PowerShell)
```powershell
$env:DB_PASSWORD="TU_PASSWORD_REAL"
```

> Cada nueva terminal requiere volver a definir la variable.

### `.env` (opcional, **NO se sube**)
```env
DB_PASSWORD=TU_PASSWORD_REAL
```

Agregar al `.gitignore`:
```gitignore
.env
```

---

## 5) Encoding (IMPORTANTE)
- `application.properties` debe estar en **UTF-8**.
- Si hay errores tipo `MalformedInputException`, re-guardar el archivo en UTF-8.

---

## 6) Arranque del proyecto

```powershell
mvn clean
$env:DB_PASSWORD="TU_PASSWORD_REAL"
mvn spring-boot:run
```

Abrir en el navegador:
```
http://localhost:8080
```

> Si aparece **Whitelabel Error Page (404)** es NORMAL: aún no hay controladores mapeados.

---

## 7) Notas importantes
- No usar `ddl-auto=create/update` por ahora.
- La estructura de BD se controla manualmente.
- El SQL se versiona solo como referencia.

---

## 8) Próximo paso
- Crear `Cliente` como `@Entity`
- `ClienteRepository`
- `ClienteController`
- Endpoint `GET /clientes`

