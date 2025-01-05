package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.PartesEmergenciaMateriales
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteEmergenciaMaterialRoutes(dao: DAOFacadeImpl) {
    route("/parte-emergencia-material") {

        // Ruta base para /parte-emergencia-material
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Parte Emergencia Material. Usa /crear, /obtener o /buscar/{id} para más acciones.")
        }

        // Obtener todas las asociaciones ParteEmergencia-Material (GET /parte-emergencia-material/obtener)
        get("/obtener") {
            try {
                val asociaciones = dao.allParteEmergenciaMaterial()
                call.respond(HttpStatusCode.OK, asociaciones)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener las asociaciones: ${e.message}"))
            }
        }

        // Crear una nueva asociación ParteEmergencia-Material (POST /parte-emergencia-material/crear)
        post("/crear") {
            try {
                val asociacion = call.receive<PartesEmergenciaMateriales>()
                val createdAsociacion = dao.createParteEmergenciaMaterial(
                    folioPEmergencia = asociacion.folioPEmergencia,
                    idMaterialP = asociacion.idMaterialP
                )
                call.respond(HttpStatusCode.Created, createdAsociacion)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear la asociación: ${e.message}"))
            }
        }

        // Obtener una asociación ParteEmergencia-Material por ID (GET /parte-emergencia-material/buscar/{id})
        get("/buscar/{id}") {
            val idparteemergenciamaterialp = call.parameters["id"]?.toIntOrNull()
            if (idparteemergenciamaterialp == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val asociacion = dao.getParteEmergenciaMaterial(idparteemergenciamaterialp)
                if (asociacion == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Asociación no encontrada."))
                } else {
                    call.respond(HttpStatusCode.OK, asociacion)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener la asociación: ${e.message}"))
            }
        }

        // Actualizar una asociación ParteEmergencia-Material (PUT /parte-emergencia-material/actualizar/{id})
        put("/actualizar/{id}") {
            val idparteemergenciamaterialp = call.parameters["id"]?.toIntOrNull()
            if (idparteemergenciamaterialp == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val asociacion = call.receive<PartesEmergenciaMateriales>()
                val updatedAsociacion = dao.updateParteEmergenciaMaterial(
                    idparteemergenciamaterialp = idparteemergenciamaterialp,
                    folioPEmergencia = asociacion.folioPEmergencia,
                    idMaterialP = asociacion.idMaterialP
                )
                call.respond(HttpStatusCode.OK, updatedAsociacion)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar la asociación: ${e.message}"))
            }
        }

        // Eliminar una asociación ParteEmergencia-Material (DELETE /parte-emergencia-material/eliminar/{id})
        delete("/eliminar/{id}") {
            val idparteemergenciamaterialp = call.parameters["id"]?.toIntOrNull()
            if (idparteemergenciamaterialp == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteParteEmergenciaMaterial(idparteemergenciamaterialp)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Asociación eliminada exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Asociación no encontrada."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar la asociación: ${e.message}"))
            }
        }
    }
}
