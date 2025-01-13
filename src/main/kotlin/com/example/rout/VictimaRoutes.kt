package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.CreateVictimasRequest
import com.example.models.Victimas
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.victimaRoutes(dao: DAOFacadeImpl) {
    route("/victima") {

        // Ruta base para /victima
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Víctima. Usa /crear, /obtener o /{idVictima} para más acciones.")
        }

        // Obtener todas las víctimas (GET /victima/obtener)
        get("/obtener") {
            try {
                val victimas = dao.allVictimas() // Método DAO para obtener todas las víctimas
                call.respond(HttpStatusCode.OK, victimas)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener víctimas: ${e.message}"))
            }
        }

        // Crear una nueva víctima (POST /victima/crear)
        post("/crear") {
            try {
                val victima = call.receive<Victimas>()

                val createdVictima = dao.createVictima(
                    rutVictima = victima.rutVictima,
                    nombreVictima = victima.nombreVictima,
                    edadVictima = victima.edadVictima,
                    descripcion = victima.descripcion,
                    folioPEmergencia = victima.folioPEmergencia
                )
                call.respond(HttpStatusCode.Created, createdVictima)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear víctima: ${e.message}"))
            }
        }

        // Obtener una víctima por ID (GET /victima/{idVictima})
        get("/{idVictima}") {
            val idVictima = call.parameters["idVictima"]?.toIntOrNull()
            if (idVictima == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de víctima inválido o faltante."))
                return@get
            }
            try {
                val victima = dao.getVictima(idVictima)
                if (victima == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Víctima no encontrada."))
                } else {
                    call.respond(HttpStatusCode.OK, victima)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener víctima: ${e.message}"))
            }
        }

        // Actualizar una víctima (PUT /victima/actualizar/{idVictima})
        put("/actualizar/{idVictima}") {
            val idVictima = call.parameters["idVictima"]?.toIntOrNull()
            if (idVictima == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de víctima inválido o faltante."))
                return@put
            }
            try {
                val victima = call.receive<Victimas>()
                val updatedVictima = dao.updateVictima(
                    idVictima = idVictima,
                    rutVictima = victima.rutVictima,
                    nombreVictima = victima.nombreVictima,
                    edadVictima = victima.edadVictima,
                    descripcion = victima.descripcion,
                    folioPEmergencia = victima.folioPEmergencia
                )
                call.respond(HttpStatusCode.OK, updatedVictima)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar víctima: ${e.message}"))
            }
        }

        // Eliminar una víctima (DELETE /victima/eliminar/{idVictima})
        delete("/eliminar/{idVictima}") {
            val idVictima = call.parameters["idVictima"]?.toIntOrNull()
            if (idVictima == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de víctima inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteVictima(idVictima)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Víctima eliminada exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Víctima no encontrada."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar víctima: ${e.message}"))
            }
        }


        post("/victimas/crear") {
            val request = call.receive<CreateVictimasRequest>()

            // Valida que el folioPEmergencia exista
            val parteEmergenciaExiste = dao.getParteEmergencia(request.folioPEmergencia) != null
            if (!parteEmergenciaExiste) {
                call.respond(HttpStatusCode.BadRequest, "El folioPEmergencia no existe.")
                return@post
            }

            // Mapea las víctimas recibidas al modelo
            val victimas = request.victimas.map { victima ->
                Victimas(
                    rutVictima = victima.rutVictima,
                    nombreVictima = victima.nombreVictima,
                    edadVictima = victima.edadVictima,
                    descripcion = victima.descripcion,
                    folioPEmergencia = request.folioPEmergencia
                )
            }

            // Inserta las víctimas usando el DAO
            val result = dao.createVictimas(victimas, request.folioPEmergencia)

            // Devuelve las víctimas creadas
            call.respond(HttpStatusCode.Created, result)
        }




    }
}
