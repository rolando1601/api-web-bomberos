package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.PartesAsistenciaVoluntarios
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteAsistenciaVoluntarioRoutes(dao: DAOFacadeImpl) {
    route("/parte-asistencia-voluntario") {

        // Ruta base para /parte-asistencia-voluntario
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Asistencia Voluntario. Usa /crear, /obtener o /{idParteAsistenciaVoluntario} para más acciones.")
        }

        // Obtener todos los registros (GET /parte-asistencia-voluntario/obtener)
        get("/obtener") {
            try {
                val partesAsistenciaVoluntarios = dao.allParteAsistenciaVoluntarios() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, partesAsistenciaVoluntarios)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener los registros: ${e.message}"))
            }
        }

        // Crear un nuevo registro (POST /parte-asistencia-voluntario/crear)
        post("/crear") {
            try {
                val parteAsistenciaVoluntario = call.receive<PartesAsistenciaVoluntarios>()

                val createdParteAsistenciaVoluntario = dao.createParteAsistenciaVoluntario(
                    folioPAsistencia = parteAsistenciaVoluntario.folioPAsistencia,
                    idVoluntario = parteAsistenciaVoluntario.idVoluntario
                )
                call.respond(HttpStatusCode.Created, createdParteAsistenciaVoluntario)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear el registro: ${e.message}"))
            }
        }

        // Obtener un registro por ID (GET /parte-asistencia-voluntario/{idParteAsistenciaVoluntario})
        get("/{idParteAsistenciaVoluntario}") {
            val idParteAsistenciaVoluntario = call.parameters["idParteAsistenciaVoluntario"]?.toIntOrNull()
            if (idParteAsistenciaVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val parteAsistenciaVoluntario = dao.getParteAsistenciaVoluntario(idParteAsistenciaVoluntario)
                if (parteAsistenciaVoluntario == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Registro no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, parteAsistenciaVoluntario)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener el registro: ${e.message}"))
            }
        }

        // Actualizar un registro (PUT /parte-asistencia-voluntario/actualizar/{idParteAsistenciaVoluntario})
        put("/actualizar/{idParteAsistenciaVoluntario}") {
            val idParteAsistenciaVoluntario = call.parameters["idParteAsistenciaVoluntario"]?.toIntOrNull()
            if (idParteAsistenciaVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val parteAsistenciaVoluntario = call.receive<PartesAsistenciaVoluntarios>()
                val updatedParteAsistenciaVoluntario = dao.updateParteAsistenciaVoluntario(
                    idParteAsistenciaVoluntario = idParteAsistenciaVoluntario,
                    folioPAsistencia = parteAsistenciaVoluntario.folioPAsistencia,
                    idVoluntario = parteAsistenciaVoluntario.idVoluntario
                )
                call.respond(HttpStatusCode.OK, updatedParteAsistenciaVoluntario)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar el registro: ${e.message}"))
            }
        }

        // Eliminar un registro (DELETE /parte-asistencia-voluntario/eliminar/{idParteAsistenciaVoluntario})
        delete("/eliminar/{idParteAsistenciaVoluntario}") {
            val idParteAsistenciaVoluntario = call.parameters["idParteAsistenciaVoluntario"]?.toIntOrNull()
            if (idParteAsistenciaVoluntario == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteAsistenciaVoluntario(idParteAsistenciaVoluntario)
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
