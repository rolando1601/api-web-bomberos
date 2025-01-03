package com.example.routes

import com.example.models.PartesAsistenciaMoviles
import com.example.dao.DAOFacadeImpl
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteAsistenciaMovilRoutes(dao: DAOFacadeImpl) {
    route("/parte-asistencia-movil") {

        // Ruta base para /parte-asistencia-movil
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Asistencia Movil. Usa /crear, /obtener o /{idParteAsistenciaMovil} para más acciones.")
        }

        // Obtener todos los registros (GET /parte-asistencia-movil/obtener)
        get("/obtener") {
            try {
                val partesAsistenciaMoviles = dao.allParteAsistenciaMovil() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, partesAsistenciaMoviles)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener los registros: ${e.message}"))
            }
        }

        // Crear un nuevo registro (POST /parte-asistencia-movil/crear)
        post("/crear") {
            try {
                val parteAsistenciaMovil = call.receive<PartesAsistenciaMoviles>()

                val createdParteAsistenciaMovil = dao.createParteAsistenciaMovil(
                    folioPAsistencia = parteAsistenciaMovil.folioPAsistencia,
                    idMovil = parteAsistenciaMovil.idMovil
                )
                call.respond(HttpStatusCode.Created, createdParteAsistenciaMovil)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear el registro: ${e.message}"))
            }
        }

        // Obtener un registro por ID (GET /parte-asistencia-movil/{idParteAsistenciaMovil})
        get("/{idParteAsistenciaMovil}") {
            val idParteAsistenciaMovil = call.parameters["idParteAsistenciaMovil"]?.toIntOrNull()
            if (idParteAsistenciaMovil == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val parteAsistenciaMovil = dao.getParteAsistenciaMovil(idParteAsistenciaMovil)
                if (parteAsistenciaMovil == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Registro no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, parteAsistenciaMovil)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener el registro: ${e.message}"))
            }
        }

        // Actualizar un registro (PUT /parte-asistencia-movil/actualizar/{idParteAsistenciaMovil})
        put("/actualizar/{idParteAsistenciaMovil}") {
            val idParteAsistenciaMovil = call.parameters["idParteAsistenciaMovil"]?.toIntOrNull()
            if (idParteAsistenciaMovil == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val parteAsistenciaMovil = call.receive<PartesAsistenciaMoviles>()
                val updatedParteAsistenciaMovil = dao.updateParteAsistenciaMovil(
                    idParteAsistenciaMovil = idParteAsistenciaMovil,
                    folioPAsistencia = parteAsistenciaMovil.folioPAsistencia,
                    idMovil = parteAsistenciaMovil.idMovil
                )
                call.respond(HttpStatusCode.OK, updatedParteAsistenciaMovil)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar el registro: ${e.message}"))
            }
        }

        // Eliminar un registro (DELETE /parte-asistencia-movil/eliminar/{idParteAsistenciaMovil})
        delete("/eliminar/{idParteAsistenciaMovil}") {
            val idParteAsistenciaMovil = call.parameters["idParteAsistenciaMovil"]?.toIntOrNull()
            if (idParteAsistenciaMovil == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteAsistenciaMovil(idParteAsistenciaMovil)
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

