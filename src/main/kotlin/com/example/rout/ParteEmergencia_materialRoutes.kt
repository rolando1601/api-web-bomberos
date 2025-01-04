package com.example.routes

import com.example.models.MaterialesP
import com.example.dao.DAOFacadeImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.parteEmergenciaMaterialPRoutes(dao: DAOFacadeImpl) {
    route("/material-p") {

        // Ruta base para /material-p
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Materiales Peligrosos. Usa /crear, /obtener o /{idMaterialP} para más acciones.")
        }

        // Obtener todos los materiales (GET /material-p/obtener)
        get("/obtener") {
            try {
                val materiales = dao.allMaterialesP() // Método DAO para obtener todos los registros
                call.respond(HttpStatusCode.OK, materiales)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener los materiales: ${e.message}"))
            }
        }

        // Crear un nuevo material (POST /material-p/crear)
        post("/crear") {
            try {
                val material = call.receive<MaterialesP>()

                val createdMaterial = dao.createMaterialP(
                    clasificacion = material.clasificacion
                )
                call.respond(HttpStatusCode.Created, createdMaterial)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear el material: ${e.message}"))
            }
        }

        // Obtener un material por ID (GET /material-p/{idMaterialP})
        get("/{idMaterialP}") {
            val idMaterialP = call.parameters["idMaterialP"]?.toIntOrNull()
            if (idMaterialP == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val material = dao.getMaterialP(idMaterialP)
                if (material == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Material no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, material)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener el material: ${e.message}"))
            }
        }

        // Actualizar un material (PUT /material-p/actualizar/{idMaterialP})
        put("/actualizar/{idMaterialP}") {
            val idMaterialP = call.parameters["idMaterialP"]?.toIntOrNull()
            if (idMaterialP == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val material = call.receive<MaterialesP>()
                val updatedMaterial = dao.updateMaterialP(
                    idMaterialP = idMaterialP,
                    clasificacion = material.clasificacion
                )
                call.respond(HttpStatusCode.OK, updatedMaterial)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar el material: ${e.message}"))
            }
        }

        // Eliminar un material (DELETE /material-p/eliminar/{idMaterialP})
        delete("/eliminar/{idMaterialP}") {
            val idMaterialP = call.parameters["idMaterialP"]?.toIntOrNull()
            if (idMaterialP == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteMaterialP(idMaterialP)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Material eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Material no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar el material: ${e.message}"))
            }
        }
    }
}
