package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.Cargos
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cargoRoutes(dao: DAOFacadeImpl) {
    route("/cargo") {

        // Ruta base para /cargo
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Cargos. Usa /crear, /obtener, /buscar/{id} o /actualizar/{id} para más acciones.")
        }

        // Obtener todos los cargos (GET /cargo/obtener)
        get("/obtener") {
            try {
                val cargos = dao.allCargos()
                call.respond(HttpStatusCode.OK, cargos)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener los cargos: ${e.message}")
                )
            }
        }

        // Crear un nuevo cargo (POST /cargo/crear)
        post("/crear") {
            try {
                val cargo = call.receive<Cargos>()
                val createdCargo = dao.createCargo(
                    idCargo = cargo.idCargo,
                    nombreCarg = cargo.nombreCarg
                )
                call.respond(HttpStatusCode.Created, createdCargo)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al crear el cargo: ${e.message}")
                )
            }
        }

        // Obtener un cargo por ID (GET /cargo/buscar/{id})
        get("/buscar/{id}") {
            val idCargo = call.parameters["id"]?.toIntOrNull()
            if (idCargo == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@get
            }
            try {
                val cargo = dao.getCargo(idCargo)
                if (cargo == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Cargo no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, cargo)
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al obtener el cargo: ${e.message}")
                )
            }
        }

        // Actualizar un cargo (PUT /cargo/actualizar/{id})
        put("/actualizar/{id}") {
            val idCargo = call.parameters["id"]?.toIntOrNull()
            if (idCargo == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@put
            }
            try {
                val cargo = call.receive<Cargos>()
                val updatedCargo = dao.updateCargo(
                    idCargo = idCargo,
                    nombreCarg = cargo.nombreCarg
                )
                call.respond(HttpStatusCode.OK, updatedCargo)
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al actualizar el cargo: ${e.message}")
                )
            }
        }

        // Eliminar un cargo (DELETE /cargo/eliminar/{id})
        delete("/eliminar/{id}") {
            val idCargo = call.parameters["id"]?.toIntOrNull()
            if (idCargo == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteCargo(idCargo)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Cargo eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Cargo no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to "Error al eliminar el cargo: ${e.message}")
                )
            }
        }
    }
}
