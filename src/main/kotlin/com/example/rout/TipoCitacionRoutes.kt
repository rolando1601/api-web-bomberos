package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.TipoCitacion
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tipoCitacionRoutes(dao: DAOFacadeImpl) {
    route("/tipo-citacion") {

        // Ruta base para /tipo-citacion
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Tipo Citación. Usa /crear, /obtener, /buscar/{id} o /actualizar/{id} para más acciones.")
        }

        // Obtener todos los tipos de citaciones (GET /tipo-citacion/obtener)
        get("/obtener") {
            try {
                val tipoCitaciones = dao.allTipoCitaciones()
                call.respond(HttpStatusCode.OK, tipoCitaciones)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener los tipos de citaciones: ${e.message}")
                )
            }
        }

        // Crear un nuevo tipo de citación (POST /tipo-citacion/crear)
        post("/crear") {
            try {
                val tipoCitacion = call.receive<TipoCitacion>()
                val createdTipoCitacion = dao.createTipoCitacion(
                    nombreTipoLlamado = tipoCitacion.nombreTipoLlamado
                )
                call.respond(HttpStatusCode.Created, createdTipoCitacion)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear el tipo de citación: ${e.message}")
                )
            }
        }

        // Obtener un tipo de citación por ID (GET /tipo-citacion/buscar/{id})
        get("/buscar/{id}") {
            val idTipoLlamado = call.parameters["id"]?.toIntOrNull()
            if (idTipoLlamado == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val tipoCitacion = dao.getTipoCitacion(idTipoLlamado)
                if (tipoCitacion == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Tipo de citación no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, tipoCitacion)
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener el tipo de citación: ${e.message}")
                )
            }
        }

        // Actualizar un tipo de citación (PUT /tipo-citacion/actualizar/{id})
        put("/actualizar/{id}") {
            val idTipoLlamado = call.parameters["id"]?.toIntOrNull()
            if (idTipoLlamado == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val tipoCitacion = call.receive<TipoCitacion>()
                val updatedTipoCitacion = dao.updateTipoCitacion(
                    idTipoLlamado = idTipoLlamado,
                    nombreTipoLlamado = tipoCitacion.nombreTipoLlamado
                )
                call.respond(HttpStatusCode.OK, updatedTipoCitacion)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar el tipo de citación: ${e.message}")
                )
            }
        }

        // Eliminar un tipo de citación (DELETE /tipo-citacion/eliminar/{id})
        delete("/eliminar/{id}") {
            val idTipoLlamado = call.parameters["id"]?.toIntOrNull()
            if (idTipoLlamado == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteTipoCitacion(idTipoLlamado)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Tipo de citación eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Tipo de citación no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar el tipo de citación: ${e.message}"))
            }
        }
    }
}
