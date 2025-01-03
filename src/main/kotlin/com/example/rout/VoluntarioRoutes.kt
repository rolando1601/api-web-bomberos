package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Voluntarios
import com.example.dao.DAOFacadeImpl

fun Route.voluntarioRoutes(dao: DAOFacadeImpl) {
    route("/voluntario") {

        // Manejar GET /voluntario directamente
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Voluntarios. Usa /crear o /obtener para más acciones.")
        }

        // Obtener todos los voluntarios (GET /voluntario/obtener)
        get("/obtener") {
            try {
                val voluntarios = dao.allVoluntarios() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, voluntarios)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener voluntarios: ${e.message}")
                )
            }
        }

        // Crear un nuevo voluntario (POST /voluntario/crear)
        post("/crear") {
            try {
                val nuevoVoluntario = call.receive<Voluntarios>() // Recibe el cuerpo como un objeto Voluntarios

                val voluntarioCreado = dao.createVoluntario(
                    nombreVol = nuevoVoluntario.nombreVol,
                    fechaNac = nuevoVoluntario.fechaNac,
                    direccion = nuevoVoluntario.direccion,
                    numeroContacto = nuevoVoluntario.numeroContacto,
                    tipoSangre = nuevoVoluntario.tipoSangre,
                    enfermedades = nuevoVoluntario.enfermedades,
                    alergias = nuevoVoluntario.alergias,
                    fechaIngreso = nuevoVoluntario.fechaIngreso,
                    claveRadial = nuevoVoluntario.claveRadial,
                    cargoVoluntario = nuevoVoluntario.cargoVoluntario,
                    rutVoluntario = nuevoVoluntario.rutVoluntario,
                    idCompania = nuevoVoluntario.idCompania,
                    idUsuario = nuevoVoluntario.idUsuario
                )
                call.respond(HttpStatusCode.Created, voluntarioCreado)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear voluntario: ${e.message}")
                )
            }
        }
        delete("/eliminar/{id}") {
            try {
                val idVoluntario = call.parameters["id"]?.toIntOrNull()
                if (idVoluntario == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@delete
                }

                val eliminado = dao.deleteVoluntario(idVoluntario)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Voluntario eliminado correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Voluntario no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar voluntario: ${e.message}")
                )
            }
        }

        // Actualizar un voluntario (PUT /voluntario/actualizar/{id})
        put("/actualizar/{id}") {
            try {
                val idVoluntario = call.parameters["id"]?.toIntOrNull()
                if (idVoluntario == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@put
                }

                val datosActualizados = call.receive<Voluntarios>()

                val voluntarioActualizado = dao.updateVoluntario(
                    idVoluntario = idVoluntario,
                    nombreVol = datosActualizados.nombreVol,
                    fechaNac = datosActualizados.fechaNac,
                    direccion = datosActualizados.direccion,
                    numeroContacto = datosActualizados.numeroContacto,
                    tipoSangre = datosActualizados.tipoSangre,
                    enfermedades = datosActualizados.enfermedades,
                    alergias = datosActualizados.alergias,
                    fechaIngreso = datosActualizados.fechaIngreso,
                    claveRadial = datosActualizados.claveRadial,
                    cargoVoluntario = datosActualizados.cargoVoluntario,
                    rutVoluntario = datosActualizados.rutVoluntario,
                    idCompania = datosActualizados.idCompania,
                    idUsuario = datosActualizados.idUsuario
                )
                call.respond(HttpStatusCode.OK, voluntarioActualizado)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar voluntario: ${e.message}"))
            }
        }
    }
}