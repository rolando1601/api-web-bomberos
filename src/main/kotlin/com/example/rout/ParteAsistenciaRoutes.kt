package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Partes_asistencia
import com.example.dao.DAOFacadeImpl

fun Route.parteAsistenciaRoutes(dao: DAOFacadeImpl) {
    route("/parte-asistencia") {


        // Manejar GET /parte-asistencia directamente
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Asistencia. Usa /crear o /obtener para más acciones.")
        }
        // Obtener todos los partes de asistencia (GET /parte-asistencia)
        get ("/obtener"){
            try {
                val partesAsistencia = dao.allParteAsistencias() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, partesAsistencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener partes de asistencia: ${e.message}"))
            }
        }
        post("/crear") {
            try {
                val parteAsistencia = call.receive<Partes_asistencia>()

                val createdParteAsistencia = dao.createParteAsistencia(
                    tipoLlamado = parteAsistencia.tipoLlamado,
                    aCargoDelCuerpo = parteAsistencia.aCargoDelCuerpo,
                    aCargoDeLaCompania = parteAsistencia.aCargoDeLaCompania,
                    fechaAsistencia = parteAsistencia.fechaAsistencia,
                    horaInicio = parteAsistencia.horaInicio,
                    horaFin = parteAsistencia.horaFin,
                    direccionAsistencia = parteAsistencia.direccionAsistencia,
                    totalAsistencia = parteAsistencia.totalAsistencia,
                    observaciones = parteAsistencia.observaciones,
                )
                call.respond(HttpStatusCode.Created, createdParteAsistencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear parte de asistencia: ${e.message}"))
            }
        }

    }
}