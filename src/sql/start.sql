-- Crear roles y asignar permisos
CREATE ROLE bd_bomberos2 WITH LOGIN PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE bd_bomberos2 TO bd_bomberos2;

-- Crear la base de datos
CREATE DATABASE bd_bomberos2 OWNER bd_bomberos2;

-- Conectarse a la base de datos
\c bd_bomberos2