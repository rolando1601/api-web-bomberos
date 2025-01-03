package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Partes_emergencia
import com.example.dao.DAOFacadeImpl
import com.example.models.Parte_emergencia

fun Route.parteEmergenciaRoutes(dao: DAOFacadeImpl) {
    route("/parte-emergencia") {

        // Manejar GET /parte-emergencia directamente
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Emergencia. Usa /crear o /obtener para más acciones.")
        }
        // Obtener todos los partes de emergencia (GET /parte-emergencia/obtener)
        get("/obtener") {
            try {
                val partesEmergencia = dao.allParteEmergencias() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, partesEmergencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener partes de emergencia: ${e.message}"))
            }
        }
        post("/crear") {
            try {
                val parteEmergencia = call.receive<Partes_emergencia>()

                val createdParteEmergencia = dao.createParteEmergencia(
                    tipoEmergencia = parteEmergencia.tipoEmergencia,
                    horaInicio = parteEmergencia.horaInicio,
                    horaFin = parteEmergencia.horaFin,
                    fechaEmergencia = parteEmergencia.fechaEmergencia,
                    preInforme = parteEmergencia.preInforme,
                    oficial = parteEmergencia.oficial,
                    folioPAsistencia = parteEmergencia.folioPAsistencia
                )
                call.respond(HttpStatusCode.Created, createdParteEmergencia) // Responder con el objeto completo
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear parte de emergencia: ${e.message}"))
            }
        }

        // Obtener un parte de emergencia por folio (GET /parte-emergencia/{folioPEmergencia})
        get("/{folioPEmergencia}") {
            val folioPEmergencia = call.parameters["folioPEmergencia"]?.toIntOrNull()
            if (folioPEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPEmergencia inválido o faltante."))
                return@get
            }
            try {
                val parteEmergencia = dao.getParteEmergencia(folioPEmergencia)
                if (parteEmergencia == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de emergencia no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, parteEmergencia)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener parte de emergencia: ${e.message}"))
            }
        }

        // Actualizar un parte de emergencia (PUT /parte-emergencia/actualizar/{folioPEmergencia})
        put("/actualizar/{folioPEmergencia}") {
            val folioPEmergencia = call.parameters["folioPEmergencia"]?.toIntOrNull()
            if (folioPEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPEmergencia inválido o faltante."))
                return@put
            }
            try {
                val parteEmergencia = call.receive<Partes_emergencia>()
                val updatedParteEmergencia = dao.updateParteEmergencia(
                    folioPEmergencia = folioPEmergencia,
                    tipoEmergencia = parteEmergencia.tipoEmergencia,
                    horaInicio = parteEmergencia.horaInicio,
                    horaFin = parteEmergencia.horaFin,
                    fechaEmergencia = parteEmergencia.fechaEmergencia,
                    preInforme = parteEmergencia.preInforme,
                    oficial = parteEmergencia.oficial,
                    folioPAsistencia = parteEmergencia.folioPAsistencia
                )
                call.respond(HttpStatusCode.OK, updatedParteEmergencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar parte de emergencia: ${e.message}"))
            }
        }

        // Eliminar un parte de emergencia (DELETE /parte-emergencia/eliminar/{folioPEmergencia})
        delete("/eliminar/{folioPEmergencia}") {
            val folioPEmergencia = call.parameters["folioPEmergencia"]?.toIntOrNull()
            if (folioPEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPEmergencia inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteEmergencia(folioPEmergencia)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Parte de emergencia eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de emergencia no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar parte de emergencia: ${e.message}"))
            }
        }
    }
}
