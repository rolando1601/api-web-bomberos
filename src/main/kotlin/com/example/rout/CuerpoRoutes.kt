package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Cuerpos
import com.example.dao.DAOFacadeImpl

fun Route.cuerpoRoutes(dao: DAOFacadeImpl) {
    route("/cuerpo") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Cuerpos. Usa /crear, /obtener, /eliminar o /actualizar para más acciones.")
        }

        // Obtener todos los cuerpos (GET /cuerpo/obtener)
        get("/obtener") {
            try {
                val cuerpos = dao.allCuerpos()
                call.respond(HttpStatusCode.OK, cuerpos)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener cuerpos: ${e.message}")
                )
            }
        }

        // Obtener un cuerpo por ID (GET /cuerpo/{id})
        get("/{id}") {
            try {
                val idCuerpo = call.parameters["id"]?.toIntOrNull()
                if (idCuerpo == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@get
                }

                val cuerpo = dao.getCuerpo(idCuerpo)
                if (cuerpo != null) {
                    call.respond(HttpStatusCode.OK, cuerpo)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Cuerpo no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener cuerpo: ${e.message}")
                )
            }
        }

        // Crear un nuevo cuerpo (POST /cuerpo/crear)
        post("/crear") {
            try {
                val nuevoCuerpo = call.receive<Cuerpos>()

                val cuerpoCreado = dao.createCuerpo(
                    nombreCuerpo = nuevoCuerpo.nombreCuerpo,
                    provincia = nuevoCuerpo.provincia,
                    region = nuevoCuerpo.region,
                    comuna = nuevoCuerpo.comuna
                )
                call.respond(HttpStatusCode.Created, cuerpoCreado)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear cuerpo: ${e.message}")
                )
            }
        }

        // Eliminar un cuerpo por ID (DELETE /cuerpo/eliminar/{id})
        delete("/eliminar/{id}") {
            try {
                val idCuerpo = call.parameters["id"]?.toIntOrNull()
                if (idCuerpo == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@delete
                }

                val eliminado = dao.deleteCuerpo(idCuerpo)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Cuerpo eliminado correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Cuerpo no encontrado"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar cuerpo: ${e.message}")
                )
            }
        }

        // Actualizar un cuerpo (PUT /cuerpo/actualizar/{id})
        put("/actualizar/{id}") {
            try {
                val idCuerpo = call.parameters["id"]?.toIntOrNull()
                if (idCuerpo == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@put
                }

                val datosActualizados = call.receive<Cuerpos>()

                val cuerpoActualizado = dao.updateCuerpo(
                    idCuerpo = idCuerpo,
                    nombreCuerpo = datosActualizados.nombreCuerpo,
                    provincia = datosActualizados.provincia,
                    region = datosActualizados.region,
                    comuna = datosActualizados.comuna
                )
                call.respond(HttpStatusCode.OK, cuerpoActualizado)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar cuerpo: ${e.message}"))
            }
        }
    }
}
