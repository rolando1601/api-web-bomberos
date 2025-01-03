package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Companias
import com.example.dao.DAOFacadeImpl

fun Route.companiaRoutes(dao: DAOFacadeImpl) {
    route("/compania") {

        // Ruta base
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Compañías. Usa /crear, /obtener, /eliminar o /actualizar para más acciones.")
        }

        // Obtener todas las compañías (GET /compania/obtener)
        get("/obtener") {
            try {
                val companias = dao.allCompanias()
                call.respond(HttpStatusCode.OK, companias)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener compañías: ${e.message}")
                )
            }
        }

        // Obtener una compañía por ID (GET /compania/{id})
        get("/{id}") {
            try {
                val idCompania = call.parameters["id"]?.toIntOrNull()
                if (idCompania == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@get
                }

                val compania = dao.getCompania(idCompania)
                if (compania != null) {
                    call.respond(HttpStatusCode.OK, compania)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Compañía no encontrada"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener compañía: ${e.message}")
                )
            }
        }

        // Crear una nueva compañía (POST /compania/crear)
        post("/crear") {
            try {
                val nuevaCompania = call.receive<Companias>()

                val companiaCreada = dao.createCompania(
                    nombreCia = nuevaCompania.nombreCia,
                    direccionCia = nuevaCompania.direccionCia,
                    especialidad = nuevaCompania.especialidad,
                    idCuerpo = nuevaCompania.idCuerpo
                )
                call.respond(HttpStatusCode.Created, companiaCreada)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear compañía: ${e.message}")
                )
            }
        }

        // Eliminar una compañía por ID (DELETE /compania/eliminar/{id})
        delete("/eliminar/{id}") {
            try {
                val idCompania = call.parameters["id"]?.toIntOrNull()
                if (idCompania == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@delete
                }

                val eliminado = dao.deleteCompania(idCompania)
                if (eliminado) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Compañía eliminada correctamente"))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Compañía no encontrada"))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar compañía: ${e.message}")
                )
            }
        }

        // Actualizar una compañía (PUT /compania/actualizar/{id})
        put("/actualizar/{id}") {
            try {
                val idCompania = call.parameters["id"]?.toIntOrNull()
                if (idCompania == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido"))
                    return@put
                }

                val datosActualizados = call.receive<Companias>()

                val companiaActualizada = dao.updateCompania(
                    idCompania = idCompania,
                    nombreCia = datosActualizados.nombreCia,
                    direccionCia = datosActualizados.direccionCia,
                    especialidad = datosActualizados.especialidad,
                    idCuerpo = datosActualizados.idCuerpo
                )
                call.respond(HttpStatusCode.OK, companiaActualizada)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar compañía: ${e.message}"))
            }
        }
    }
}
