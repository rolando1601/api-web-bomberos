package com.example.routes

import com.example.models.PartesEmergenciaVoluntarios
import com.example.dao.DAOFacadeImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteEmergenciaVoluntarioRoutes(dao: DAOFacadeImpl) {
    route("/parte-emergencia-voluntario") {

        // Ruta base para /parte-emergencia-voluntario
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Emergencia Voluntario. Usa /crear, /obtener o /{idParteVoluntario} para más acciones.")
        }

        // Obtener todos los registros (GET /parte-emergencia-voluntario/obtener)
        get("/obtener") {
            try {
                val partesEmergenciaVoluntarios = dao.allParteEmergenciaVoluntarios() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, partesEmergenciaVoluntarios)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener los registros: ${e.message}"))
            }
        }

        // Crear un nuevo registro (POST /parte-emergencia-voluntario/crear)
        post("/crear") {
            try {
                val parteEmergenciaVoluntario = call.receive<PartesEmergenciaVoluntarios>()

                val createdParteEmergenciaVoluntario = dao.createParteEmergenciaVoluntario(
                    folioPEmergencia = parteEmergenciaVoluntario.folioPEmergencia,
                    idVoluntario = parteEmergenciaVoluntario.idVoluntario
                )
                call.respond(HttpStatusCode.Created, createdParteEmergenciaVoluntario)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear el registro: ${e.message}"))
            }
        }

        // Obtener un registro por ID (GET /parte-emergencia-voluntario/{idParteVoluntario})
        get("/{idParteVoluntario}") {
            val idParteVoluntario = call.parameters["idParteVoluntario"]?.toIntOrNull()
            if (idParteVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val parteEmergenciaVoluntario = dao.getParteEmergenciaVoluntario(idParteVoluntario)
                if (parteEmergenciaVoluntario == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Registro no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, parteEmergenciaVoluntario)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener el registro: ${e.message}"))
            }
        }

        // Actualizar un registro (PUT /parte-emergencia-voluntario/actualizar/{idParteVoluntario})
        put("/actualizar/{idParteVoluntario}") {
            val idParteVoluntario = call.parameters["idParteVoluntario"]?.toIntOrNull()
            if (idParteVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val parteEmergenciaVoluntario = call.receive<PartesEmergenciaVoluntarios>()
                val updatedParteEmergenciaVoluntario = dao.updateParteEmergenciaVoluntario(
                    idParteVoluntario = idParteVoluntario,
                    folioPEmergencia = parteEmergenciaVoluntario.folioPEmergencia,
                    idVoluntario = parteEmergenciaVoluntario.idVoluntario
                )
                call.respond(HttpStatusCode.OK, updatedParteEmergenciaVoluntario)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar el registro: ${e.message}"))
            }
        }

        // Eliminar un registro (DELETE /parte-emergencia-voluntario/eliminar/{idParteVoluntario})
        delete("/eliminar/{idParteVoluntario}") {
            val idParteVoluntario = call.parameters["idParteVoluntario"]?.toIntOrNull()
            if (idParteVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteEmergenciaVoluntario(idParteVoluntario)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Registro eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Registro no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar el registro: ${e.message}"))
            }
        }
    }
}
