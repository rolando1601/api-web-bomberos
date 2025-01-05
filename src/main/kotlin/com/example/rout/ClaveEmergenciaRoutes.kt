package com.example.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.ClaveEmergencias
import com.example.dao.DAOFacadeImpl

fun Route.claveEmergenciaRoutes(dao: DAOFacadeImpl) {
    route("/claveEmergencia") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de ClaveEmergencias. Usa /crear, /obtener, /actualizar, /eliminar o /buscar para más acciones.")
        }

        // Obtener todas las claves de emergencias
        get("/obtener") {
            try {
                val claves = dao.allClaveEmergencias()
                call.respond(HttpStatusCode.OK, claves)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener claves de emergencia: ${e.message}")
                )
            }
        }

        // Buscar una clave de emergencia por ID
        get("/buscar/{id}") {
            val idClaveEmergencia = call.parameters["id"]?.toIntOrNull()
            if (idClaveEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@get
            }
            try {
                val clave = dao.getClaveEmergencia(idClaveEmergencia)
                if (clave != null) {
                    call.respond(HttpStatusCode.OK, clave)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Clave de emergencia no encontrada"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al buscar clave de emergencia: ${e.message}")
                )
            }
        }

        // Crear una nueva clave de emergencia
        post("/crear") {
            try {
                val nuevaClave = call.receive<ClaveEmergencias>()
                val claveCreada = dao.createClaveEmergencia(
                    nombreClaveEmergencia = nuevaClave.nombreClaveEmergencia
                )
                call.respond(HttpStatusCode.Created, claveCreada)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear clave de emergencia: ${e.message}")
                )
            }
        }

        // Eliminar una clave de emergencia por ID
        delete("/eliminar/{id}") {
            val idClaveEmergencia = call.parameters["id"]?.toIntOrNull()
            if (idClaveEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@delete
            }
            try {
                val eliminado = dao.deleteClaveEmergencia(idClaveEmergencia)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Clave de emergencia eliminada correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Clave de emergencia no encontrada"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar clave de emergencia: ${e.message}")
                )
            }
        }

        // Actualizar una clave de emergencia por ID
        put("/actualizar/{id}") {
            val idClaveEmergencia = call.parameters["id"]?.toIntOrNull()
            if (idClaveEmergencia == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                return@put
            }
            try {
                val datosActualizados = call.receive<ClaveEmergencias>()
                val claveActualizada = dao.updateClaveEmergencia(
                    idClaveEmergencia = idClaveEmergencia,
                    nombreClaveEmergencia = datosActualizados.nombreClaveEmergencia
                )
                call.respond(HttpStatusCode.OK, claveActualizada)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar clave de emergencia: ${e.message}")
                )
            }
        }
    }
}
