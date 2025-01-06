package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.Partes_emergencia
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteEmergenciaRoutes(dao: DAOFacadeImpl) {
    route("/parte-emergencia") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Emergencia. Usa /crear, /obtener, /actualizar, /eliminar o /buscar para más acciones.")
        }

        // Obtener todos los partes de emergencia
        get("/obtener") {
            try {
                val partesEmergencia = dao.allPartesEmergencia()
                call.respond(HttpStatusCode.OK, partesEmergencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener partes de emergencia: ${e.message}"))
            }
        }

        // Crear un nuevo parte de emergencia
        post("/crear") {
            try {
                val parteEmergencia = call.receive<Partes_emergencia>()
                val createdParteEmergencia = dao.createParteEmergencia(
                    horaInicio = parteEmergencia.horaInicio,
                    horaFin = parteEmergencia.horaFin,
                    fechaEmergencia = parteEmergencia.fechaEmergencia,
                    preInforme = parteEmergencia.preInforme,
                    llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                    descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                    direccionEmergencia = parteEmergencia.direccionEmergencia,
                    idOficial = parteEmergencia.idOficial,
                    idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                    folioPAsistencia = parteEmergencia.folioPAsistencia
                )
                call.respond(HttpStatusCode.Created, createdParteEmergencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear parte de emergencia: ${e.message}"))
            }
        }

        // Buscar un parte de emergencia por folio
        get("/buscar/{folioPEmergencia}") {
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

        // Actualizar un parte de emergencia
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
                    horaInicio = parteEmergencia.horaInicio,
                    horaFin = parteEmergencia.horaFin,
                    fechaEmergencia = parteEmergencia.fechaEmergencia,
                    preInforme = parteEmergencia.preInforme,
                    llamarEmpresaQuimica = parteEmergencia.llamarEmpresaQuimica,
                    descripcionMaterialP = parteEmergencia.descripcionMaterialP,
                    direccionEmergencia = parteEmergencia.direccionEmergencia,
                    idOficial = parteEmergencia.idOficial,
                    idClaveEmergencia = parteEmergencia.idClaveEmergencia,
                    folioPAsistencia = parteEmergencia.folioPAsistencia
                )
                call.respond(HttpStatusCode.OK, updatedParteEmergencia)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar parte de emergencia: ${e.message}"))
            }
        }

        // Eliminar un parte de emergencia
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
