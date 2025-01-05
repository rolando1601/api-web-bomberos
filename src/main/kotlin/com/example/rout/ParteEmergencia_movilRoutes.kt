package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.PartesEmergenciaMoviles
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteEmergenciaMovilRoutes(dao: DAOFacadeImpl) {
    route("/parte-emergencia-movil") {

        // Ruta base para /parte-emergencia-movil
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Emergencia Movil. Usa /crear, /obtener o /{idParteEmergenciaMovil} para más acciones.")
        }

        // Obtener todos los registros (GET /parte-emergencia-movil/obtener)
        get("/obtener") {
            try {
                val partesEmergenciaMoviles = dao.allParteEmergenciaMovil() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, partesEmergenciaMoviles)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener los registros: ${e.message}"))
            }
        }

        // Crear un nuevo registro (POST /parte-emergencia-movil/crear)
        post("/crear") {
            try {
                val parteEmergenciaMovil = call.receive<PartesEmergenciaMoviles>()

                val createdParteEmergenciaMovil = dao.createParteEmergenciaMovil(
                    folioPEmergencia = parteEmergenciaMovil.folioPEmergencia,
                    idMovil = parteEmergenciaMovil.idMovil
                )
                call.respond(HttpStatusCode.Created, createdParteEmergenciaMovil)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear el registro: ${e.message}"))
            }
        }

        // Obtener un registro por ID (GET /parte-emergencia-movil/{idParteEmergenciaMovil})
        get("/{idParteEmergenciaMovil}") {
            val idParteEmergenciaMovil = call.parameters["idParteEmergenciaMovil"]?.toIntOrNull()
            if (idParteEmergenciaMovil == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val parteEmergenciaMovil = dao.getParteEmergenciaMovil(idParteEmergenciaMovil)
                if (parteEmergenciaMovil == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Registro no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, parteEmergenciaMovil)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener el registro: ${e.message}"))
            }
        }

        // Actualizar un registro (PUT /parte-emergencia-movil/actualizar/{idParteEmergenciaMovil})
        put("/actualizar/{idParteEmergenciaMovil}") {
            val idParteEmergenciaMovil = call.parameters["idParteEmergenciaMovil"]?.toIntOrNull()
            if (idParteEmergenciaMovil == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val parteEmergenciaMovil = call.receive<PartesEmergenciaMoviles>()
                val updatedParteEmergenciaMovil = dao.updateParteEmergenciaMovil(
                    idParteEmergenciaMovil = idParteEmergenciaMovil,
                    folioPEmergencia = parteEmergenciaMovil.folioPEmergencia,
                    idMovil = parteEmergenciaMovil.idMovil
                )
                call.respond(HttpStatusCode.OK, updatedParteEmergenciaMovil)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar el registro: ${e.message}"))
            }
        }

        // Eliminar un registro (DELETE /parte-emergencia-movil/eliminar/{idParteEmergenciaMovil})
        delete("/eliminar/{idParteEmergenciaMovil}") {
            val idParteEmergenciaMovil = call.parameters["idParteEmergenciaMovil"]?.toIntOrNull()
            if (idParteEmergenciaMovil == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteEmergenciaMovil(idParteEmergenciaMovil)
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
