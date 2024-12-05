-- Crear roles y asignar permisos
CREATE ROLE user_bomberos WITH LOGIN PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE bd_prueba_bomberos TO user_bomberos;

-- Crear la base de datos
CREATE DATABASE bd_prueba_bomberos OWNER user_bomberos;

-- Conectarse a la base de datos
\c bd_prueba_bomberos;