package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.Instituciones
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.institucionRoutes(dao: DAOFacadeImpl) {
    route("/institucion") {

        // Ruta base para /institucion
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Institución. Usa /crear, /obtener o /{idInstitucion} para más acciones.")
        }

        // Obtener todas las instituciones (GET /institucion/obtener)
        get("/obtener") {
            try {
                val instituciones = dao.allInstituciones() // Método DAO para obtener todas las instituciones
                call.respond(HttpStatusCode.OK, instituciones)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener instituciones: ${e.message}"))
            }
        }

        // Crear una nueva institución (POST /institucion/crear)
        post("/crear") {
            try {
                val institucion = call.receive<Instituciones>()

                val createdInstitucion = dao.createInstitucion(
                    nombreInstitucion = institucion.nombreInstitucion,
                    tipoInstitucion = institucion.tipoInstitucion,
                    nombrePersonaCargo = institucion.nombrePersonaCargo,
                    horaLlegada = institucion.horaLlegada,
                    folioPEmergencia = institucion.folioPEmergencia
                )
                call.respond(HttpStatusCode.Created, createdInstitucion)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear institución: ${e.message}"))
            }
        }

        // Obtener una institución por ID (GET /institucion/{idInstitucion})
        get("/{idInstitucion}") {
            val idInstitucion = call.parameters["idInstitucion"]?.toIntOrNull()
            if (idInstitucion == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de institución inválido o faltante."))
                return@get
            }
            try {
                val institucion = dao.getInstitucion(idInstitucion)
                if (institucion == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Institución no encontrada."))
                } else {
                    call.respond(HttpStatusCode.OK, institucion)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener institución: ${e.message}"))
            }
        }

        // Actualizar una institución (PUT /institucion/actualizar/{idInstitucion})
        put("/actualizar/{idInstitucion}") {
            val idInstitucion = call.parameters["idInstitucion"]?.toIntOrNull()
            if (idInstitucion == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de institución inválido o faltante."))
                return@put
            }
            try {
                val institucion = call.receive<Instituciones>()
                val updatedInstitucion = dao.updateInstitucion(
                    idInstitucion = idInstitucion,
                    nombreInstitucion = institucion.nombreInstitucion,
                    tipoInstitucion = institucion.tipoInstitucion,
                    nombrePersonaCargo = institucion.nombrePersonaCargo,
                    horaLlegada = institucion.horaLlegada,
                    folioPEmergencia = institucion.folioPEmergencia
                )
                call.respond(HttpStatusCode.OK, updatedInstitucion)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar institución: ${e.message}"))
            }
        }

        // Eliminar una institución (DELETE /institucion/eliminar/{idInstitucion})
        delete("/eliminar/{idInstitucion}") {
            val idInstitucion = call.parameters["idInstitucion"]?.toIntOrNull()
            if (idInstitucion == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de institución inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteInstitucion(idInstitucion)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Institución eliminada exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Institución no encontrada."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar institución: ${e.message}"))
            }
        }
    }
}
