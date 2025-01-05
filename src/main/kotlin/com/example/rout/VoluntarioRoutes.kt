package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.Voluntarios
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.voluntarioRoutes(dao: DAOFacadeImpl) {
    route("/voluntario") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Voluntarios. Usa /crear, /obtener, /actualizar, /eliminar o /buscar para más acciones.")
        }

        // Obtener todos los voluntarios
        get("/obtener") {
            try {
                val voluntarios = dao.allVoluntarios()
                call.respond(HttpStatusCode.OK, voluntarios)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener voluntarios: ${e.message}")
                )
            }
        }

        // Buscar un voluntario por ID
        get("/buscar/{id}") {
            val idVoluntario = call.parameters["id"]?.toIntOrNull()
            if (idVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@get
            }
            try {
                val voluntario = dao.getVoluntario(idVoluntario)
                if (voluntario != null) {
                    call.respond(HttpStatusCode.OK, voluntario)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Voluntario no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al buscar voluntario: ${e.message}")
                )
            }
        }

        // Crear un nuevo voluntario
        post("/crear") {
            try {
                val nuevoVoluntario = call.receive<Voluntarios>()
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
                    rutVoluntario = nuevoVoluntario.rutVoluntario,
                    idCompania = nuevoVoluntario.idCompania,
                    idUsuario = nuevoVoluntario.idUsuario,
                    idCargo = nuevoVoluntario.idCargo
                )
                call.respond(HttpStatusCode.Created, voluntarioCreado)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear voluntario: ${e.message}")
                )
            }
        }

        // Eliminar un voluntario por ID
        delete("/eliminar/{id}") {
            val idVoluntario = call.parameters["id"]?.toIntOrNull()
            if (idVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@delete
            }
            try {
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

        // Actualizar un voluntario por rut
        put("/actualizar/{rutVoluntario}") {
            val rutVoluntario = call.parameters["rutVoluntario"]
            if (rutVoluntario.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Rut del voluntario es requerido"))
                return@put
            }
            try {
                val datosActualizados = call.receive<Voluntarios>()
                if (rutVoluntario != datosActualizados.rutVoluntario) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "El rut en la URL no coincide con el rut en los datos enviados"))
                    return@put
                }

                val idVoluntario = dao.getIdVoluntarioByRut(rutVoluntario)

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
                    rutVoluntario = datosActualizados.rutVoluntario,
                    idCompania = datosActualizados.idCompania,
                    idUsuario = datosActualizados.idUsuario,
                    idCargo = datosActualizados.idCargo
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
