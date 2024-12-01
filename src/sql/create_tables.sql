CREATE TABLE "institucion" (
                               "idInstitucion" integer PRIMARY KEY,
                               "nombreInstitucion" varchar,
                               "tipoInstitucion" varchar,
                               "nombrePersonaCargo" varchar,
                               "horaLlegada" time,
                               "folioPEmergencia" integer
);

CREATE TABLE "cuerpo" (
                          "idCuerpo" integer PRIMARY KEY,
                          "nombreCuerpo" varchar,
                          "provincia" varchar,
                          "region" varchar,
                          "comuna" varchar
);

CREATE TABLE "compania" (
                            "idCompania" integer PRIMARY KEY,
                            "nombreCia" varchar,
                            "direccionCia" varchar,
                            "especialidad" varchar,
                            "idCuerpo" integer
);

CREATE TABLE "usuario" (
                           "idUsuario" integer PRIMARY KEY,
                           "nombreUsuario" varchar,
                           "contrasena" varchar,
                           "rol" varchar
);

CREATE TABLE "voluntario" (
                              "idVoluntario" integer PRIMARY KEY,
                              "nombreVol" varchar,
                              "fechaNac" date,
                              "direccion" varchar,
                              "numeroContacto" varchar,
                              "tipoSangre" varchar,
                              "enfermedades" text,
                              "alergias" text,
                              "fechaIngreso" date,
                              "claveRadial" text,
                              "cargoVoluntario" text,
                              "idUsuario" integer,
                              "idCompania" integer,
                              "folioPEmergencia" integer,
                              "folioPAsistencia" integer
);

CREATE TABLE "inmueble" (
                            "idInmueble" integer PRIMARY KEY,
                            "direccion" varchar,
                            "tipoInmueble" varchar,
                            "estadoInmueble" varchar,
                            "folioPEmergencia" integer
);

CREATE TABLE "victima" (
                           "rutVictima" varchar PRIMARY KEY,
                           "nombreVictima" varchar,
                           "edadVictima" integer,
                           "folioPEmergencia" integer
);

CREATE TABLE "vehiculo" (
                            "patente" varchar PRIMARY KEY,
                            "marca" varchar,
                            "modelo" varchar,
                            "tipoVehiculo" varchar,
                            "folioPEmergencia" integer
);

CREATE TABLE "emergencia" (
                              "idEmergencia" integer PRIMARY KEY,
                              "claveEmergencia" varchar,
                              "cuadrante" varchar,
                              "direccion" varchar
);

CREATE TABLE "parteEmergencia" (
                                   "folioPEmergencia" integer PRIMARY KEY,
                                   "tipoEmergencia" varchar,
                                   "naturaleza" text,
                                   "tipoNaturaleza" varchar,
                                   "horaInicio" time,
                                   "horaFin" time,
                                   "fechaEmergencia" date,
                                   "preInforme" text,
                                   "oficial" text,
                                   "idEmergencia" integer,
                                   "folioPAsistencia" integer
);

CREATE TABLE "parteAsistencia" (
                                   "folioPAsistencia" integer PRIMARY KEY,
                                   "tipoLlamado" varchar,
                                   "aCargoDelCuerpo" varchar,
                                   "aCargoDeLaCompania" varchar,
                                   "fechaAsistencia" date,
                                   "horaInicio" time,
                                   "horaFin" time,
                                   "direccion" varchar,
                                   "totalAsistencia" integer,
                                   "observaciones" text,
                                   "materialMayorAsistencia" text
);

CREATE TABLE "materialP" (
                               "idMaterialesP" integer PRIMARY KEY,
                               "numeroONU" varchar,
                               "clasificacion" varchar,
                               "nombre" varchar,
                               "folioPEmergencia" integer
);

ALTER TABLE "institucion" ADD FOREIGN KEY ("folioPEmergencia") REFERENCES "parteEmergencia" ("folioPEmergencia");

ALTER TABLE "compania" ADD FOREIGN KEY ("idCuerpo") REFERENCES "cuerpo" ("idCuerpo");

ALTER TABLE "voluntario" ADD FOREIGN KEY ("idUsuario") REFERENCES "usuario" ("idUsuario");

ALTER TABLE "voluntario" ADD FOREIGN KEY ("idCompania") REFERENCES "compania" ("idCompania");

-- ALTER TABLE "voluntario" ADD FOREIGN KEY ("folioPEmergencia") REFERENCES "parteEmergencia" ("folioPEmergencia");

-- ALTER TABLE "voluntario" ADD FOREIGN KEY ("folioPAsistencia") REFERENCES "parteAsistencia" ("folioPAsistencia");

ALTER TABLE "inmueble" ADD FOREIGN KEY ("folioPEmergencia") REFERENCES "parteEmergencia" ("folioPEmergencia");

ALTER TABLE "victima" ADD FOREIGN KEY ("folioPEmergencia") REFERENCES "parteEmergencia" ("folioPEmergencia");

ALTER TABLE "vehiculo" ADD FOREIGN KEY ("folioPEmergencia") REFERENCES "parteEmergencia" ("folioPEmergencia");

ALTER TABLE "parteEmergencia" ADD FOREIGN KEY ("idEmergencia") REFERENCES "emergencia" ("idEmergencia");

-- ALTER TABLE "parteEmergencia" ADD FOREIGN KEY ("folioPAsistencia") REFERENCES "parteAsistencia" ("folioPAsistencia");

ALTER TABLE "materialesP" ADD FOREIGN KEY ("folioPEmergencia") REFERENCES "parteEmergencia" ("folioPEmergencia");
