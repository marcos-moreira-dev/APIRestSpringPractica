DROP DATABASE IF EXISTS db_springboot_dev;

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

