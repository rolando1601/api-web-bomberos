package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Emergencias
import com.example.dao.DAOFacadeImpl

fun Route.emergenciaRoutes(dao: DAOFacadeImpl) {
    route("/emergencia") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Emergencias. Usa /crear, /obtener, /eliminar o /actualizar para más acciones.")
        }

        // Obtener todas las emergencias (GET /emergencia/obtener)
        get("/obtener") {
            try {
                val emergencias = dao.allEmergencias()
                call.respond(HttpStatusCode.OK, emergencias)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener emergencias: ${e.message}")
                )
            }
        }

        // Obtener una emergencia por ID (GET /emergencia/{id})
        get("/{id}") {
            try {
                val idEmergencia = call.parameters["id"]?.toIntOrNull()
                if (idEmergencia == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@get
                }

                val emergencia = dao.getEmergencia(idEmergencia)
                if (emergencia != null) {
                    call.respond(HttpStatusCode.OK, emergencia)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Emergencia no encontrada"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener emergencia: ${e.message}")
                )
            }
        }

        // Crear una nueva emergencia (POST /emergencia/crear)
        post("/crear") {
            try {
                val nuevaEmergencia = call.receive<Emergencias>()

                val emergenciaCreada = dao.createEmergencia(
                    claveEmergencia = nuevaEmergencia.claveEmergencia,
                    cuadrante = nuevaEmergencia.cuadrante,
                    direccionEmergencia = nuevaEmergencia.direccionEmergencia,
                    folioPEmergencia = nuevaEmergencia.folioPEmergencia
                )
                call.respond(HttpStatusCode.Created, emergenciaCreada)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear emergencia: ${e.message}")
                )
            }
        }

        // Eliminar una emergencia por ID (DELETE /emergencia/eliminar/{id})
        delete("/eliminar/{id}") {
            try {
                val idEmergencia = call.parameters["id"]?.toIntOrNull()
                if (idEmergencia == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@delete
                }

                val eliminado = dao.deleteEmergencia(idEmergencia)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Emergencia eliminada correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Emergencia no encontrada"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar emergencia: ${e.message}")
                )
            }
        }

        // Actualizar una emergencia (PUT /emergencia/actualizar/{id})
        put("/actualizar/{id}") {
            try {
                val idEmergencia = call.parameters["id"]?.toIntOrNull()
                if (idEmergencia == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@put
                }

                val datosActualizados = call.receive<Emergencias>()

                val emergenciaActualizada = dao.updateEmergencia(
                    idEmergencia = idEmergencia,
                    claveEmergencia = datosActualizados.claveEmergencia,
                    cuadrante = datosActualizados.cuadrante,
                    direccionEmergencia = datosActualizados.direccionEmergencia,
                    folioPEmergencia = datosActualizados.folioPEmergencia
                )
                call.respond(HttpStatusCode.OK, emergenciaActualizada)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar emergencia: ${e.message}"))
            }
        }
    }
}
