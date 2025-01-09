package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.Partes_asistencia
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteAsistenciaRoutes(dao: DAOFacadeImpl) {
    route("/parte-asistencia") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Asistencia. Usa /crear, /obtener, /actualizar, /eliminar o /buscar para más acciones.")
        }

        // Obtener todos los partes de asistencia
        get("/obtener") {
            try {
                val partesAsistencia = dao.allPartesAsistencia()
                call.respond(HttpStatusCode.OK, partesAsistencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener partes de asistencia: ${e.message}")
                )
            }
        }

        // Crear un nuevo parte de asistencia
        post("/crear") {
            try {
                val parteAsistencia = call.receive<Partes_asistencia>()
                val createdParteAsistencia = dao.createParteAsistencia(
                    aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                    aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                    fechaAsistencia = parteAsistencia.fechaAsistencia,
                    horaInicio = parteAsistencia.horaInicio,
                    horaFin = parteAsistencia.horaFin,
                    direccionAsistencia = parteAsistencia.direccionAsistencia,
                    totalAsistencia = parteAsistencia.totalAsistencia,
                    observaciones = parteAsistencia.observaciones,
                    idTipoLlamado = parteAsistencia.idTipoLlamado
                )
                call.respond(HttpStatusCode.Created, createdParteAsistencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear parte de asistencia: ${e.message}")
                )
            }
        }

        // Buscar un parte de asistencia por folio
        get("/buscar/{folioPAsistencia}") {
            val folioPAsistencia = call.parameters["folioPAsistencia"]?.toIntOrNull()
            if (folioPAsistencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPAsistencia inválido o faltante."))
                return@get
            }
            try {
                val parteAsistencia = dao.getParteAsistencia(folioPAsistencia)
                if (parteAsistencia == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de asistencia no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, parteAsistencia)
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener parte de asistencia: ${e.message}")
                )
            }
        }

        // Actualizar un parte de asistencia
        put("/actualizar/{folioPAsistencia}") {
            val folioPAsistencia = call.parameters["folioPAsistencia"]?.toIntOrNull()
            if (folioPAsistencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPAsistencia inválido o faltante."))
                return@put
            }
            try {
                val parteAsistencia = call.receive<Partes_asistencia>()
                val updatedParteAsistencia = dao.updateParteAsistencia(
                    folioPAsistencia = folioPAsistencia,
                    aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                    aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                    fechaAsistencia = parteAsistencia.fechaAsistencia,
                    horaInicio = parteAsistencia.horaInicio,
                    horaFin = parteAsistencia.horaFin,
                    direccionAsistencia = parteAsistencia.direccionAsistencia,
                    totalAsistencia = parteAsistencia.totalAsistencia,
                    observaciones = parteAsistencia.observaciones,
                    idTipoLlamado = parteAsistencia.idTipoLlamado
                )
                call.respond(HttpStatusCode.OK, updatedParteAsistencia)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar parte de asistencia: ${e.message}")
                )
            }
        }

        // Eliminar un parte de asistencia
        delete("/eliminar/{folioPAsistencia}") {
            val folioPAsistencia = call.parameters["folioPAsistencia"]?.toIntOrNull()
            if (folioPAsistencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "FolioPAsistencia inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteAsistencia(folioPAsistencia)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Parte de asistencia eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Parte de asistencia no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar parte de asistencia: ${e.message}")
                )
            }
        }
        // Guardar un parte de asistencia (crear o actualizar)
        post("/guardar") {
            try {
                val parteAsistencia = call.receive<Partes_asistencia>()

                // Si el folioPAsistencia es nulo, se crea un nuevo registro
                if (parteAsistencia.folioPAsistencia == null) {
                    val createdParteAsistencia = dao.createParteAsistencia(
                        aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                        aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                        fechaAsistencia = parteAsistencia.fechaAsistencia,
                        horaInicio = parteAsistencia.horaInicio,
                        horaFin = parteAsistencia.horaFin,
                        direccionAsistencia = parteAsistencia.direccionAsistencia,
                        totalAsistencia = parteAsistencia.totalAsistencia,
                        observaciones = parteAsistencia.observaciones,
                        idTipoLlamado = parteAsistencia.idTipoLlamado
                    )
                    call.respond(HttpStatusCode.Created, createdParteAsistencia)
                } else {
                    // Si el folioPAsistencia existe, se actualiza el registro
                    val updatedParteAsistencia = dao.updateParteAsistencia(
                        folioPAsistencia = parteAsistencia.folioPAsistencia,
                        aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                        aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                        fechaAsistencia = parteAsistencia.fechaAsistencia,
                        horaInicio = parteAsistencia.horaInicio,
                        horaFin = parteAsistencia.horaFin,
                        direccionAsistencia = parteAsistencia.direccionAsistencia,
                        totalAsistencia = parteAsistencia.totalAsistencia,
                        observaciones = parteAsistencia.observaciones,
                        idTipoLlamado = parteAsistencia.idTipoLlamado
                    )
                    call.respond(HttpStatusCode.OK, updatedParteAsistencia)
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al guardar parte de asistencia: ${e.message}")
                )
            }
        }

    }
}
