package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.MaterialesP
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.materialPRoutes(dao: DAOFacadeImpl) {
    route("/materialP") {

        // Ruta base para /materialP
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Material Peligroso. Usa /crear, /obtener, /actualizar o /buscar para más acciones.")
        }

        // Obtener todos los materiales peligrosos (GET /materialP/obtener)
        get("/obtener") {
            try {
                val materialesP = dao.allMaterialesP()
                call.respond(HttpStatusCode.OK, materialesP)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener materiales peligrosos: ${e.message}")
                )
            }
        }

        // Crear un nuevo material peligroso (POST /materialP/crear)
        post("/crear") {
            try {
                val materialP = call.receive<MaterialesP>()
                val createdMaterialP = dao.createMaterialP(
                    clasificacion = materialP.clasificacion
                )
                call.respond(HttpStatusCode.Created, createdMaterialP)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear material peligroso: ${e.message}")
                )
            }
        }

        // Obtener un material peligroso por ID (GET /materialP/buscar/{idMaterialP})
        get("/buscar/{idMaterialP}") {
            val idMaterialP = call.parameters["idMaterialP"]?.toIntOrNull()
            if (idMaterialP == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de material peligroso inválido o faltante."))
                return@get
            }
            try {
                val materialP = dao.getMaterialP(idMaterialP)
                if (materialP == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Material peligroso no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, materialP)
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener material peligroso: ${e.message}")
                )
            }
        }

        // Actualizar un material peligroso (PUT /materialP/actualizar/{idMaterialP})
        put("/actualizar/{idMaterialP}") {
            val idMaterialP = call.parameters["idMaterialP"]?.toIntOrNull()
            if (idMaterialP == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de material peligroso inválido o faltante."))
                return@put
            }
            try {
                val materialP = call.receive<MaterialesP>()
                val updatedMaterialP = dao.updateMaterialP(
                    idMaterialP = idMaterialP,
                    clasificacion = materialP.clasificacion
                )
                call.respond(HttpStatusCode.OK, updatedMaterialP)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar material peligroso: ${e.message}")
                )
            }
        }

        // Eliminar un material peligroso (DELETE /materialP/eliminar/{idMaterialP})
        delete("/eliminar/{idMaterialP}") {
            val idMaterialP = call.parameters["idMaterialP"]?.toIntOrNull()
            if (idMaterialP == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de material peligroso inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteMaterialP(idMaterialP)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Material peligroso eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Material peligroso no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar material peligroso: ${e.message}")
                )
            }
        }
    }
}
