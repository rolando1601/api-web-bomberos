package com.example.routes

import com.example.dao.DAOFacadeImpl
import com.example.models.CreateInmueblesRequest
import com.example.models.Inmuebles
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.inmuebleRoutes(dao: DAOFacadeImpl) {
    route("/inmueble") {

        // Ruta base para /inmueble
        get {
            call.respond(HttpStatusCode.OK, "Ruta base de Inmueble. Usa /crear, /obtener o /{idInmueble} para más acciones.")
        }

        // Obtener todos los inmuebles (GET /inmueble/obtener)
        get("/obtener") {
            try {
                val inmuebles = dao.allInmuebles() // Método DAO para obtener todos los inmuebles
                call.respond(HttpStatusCode.OK, inmuebles)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener inmuebles: ${e.message}"))
            }
        }

        // Crear un nuevo inmueble (POST /inmueble/crear)
        post("/crear") {
            try {
                val inmueble = call.receive<Inmuebles>()

                val createdInmueble = dao.createInmueble(
                    direccion = inmueble.direccion,
                    tipoInmueble = inmueble.tipoInmueble,
                    estadoInmueble = inmueble.estadoInmueble,
                    folioPEmergencia = inmueble.folioPEmergencia
                )
                call.respond(HttpStatusCode.Created, createdInmueble)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al crear inmueble: ${e.message}"))
            }
        }

        // Obtener un inmueble por ID (GET /inmueble/{idInmueble})
        get("/{idInmueble}") {
            val idInmueble = call.parameters["idInmueble"]?.toIntOrNull()
            if (idInmueble == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de inmueble inválido o faltante."))
                return@get
            }
            try {
                val inmueble = dao.getInmueble(idInmueble)
                if (inmueble == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Inmueble no encontrado."))
                } else {
                    call.respond(HttpStatusCode.OK, inmueble)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al obtener inmueble: ${e.message}"))
            }
        }

        // Actualizar un inmueble (PUT /inmueble/actualizar/{idInmueble})
        put("/actualizar/{idInmueble}") {
            val idInmueble = call.parameters["idInmueble"]?.toIntOrNull()
            if (idInmueble == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de inmueble inválido o faltante."))
                return@put
            }
            try {
                val inmueble = call.receive<Inmuebles>()
                val updatedInmueble = dao.updateInmueble(
                    idInmueble = idInmueble,
                    direccion = inmueble.direccion,
                    tipoInmueble = inmueble.tipoInmueble,
                    estadoInmueble = inmueble.estadoInmueble,
                    folioPEmergencia = inmueble.folioPEmergencia
                )
                call.respond(HttpStatusCode.OK, updatedInmueble)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al actualizar inmueble: ${e.message}"))
            }
        }

        // Eliminar un inmueble (DELETE /inmueble/eliminar/{idInmueble})
        delete("/eliminar/{idInmueble}") {
            val idInmueble = call.parameters["idInmueble"]?.toIntOrNull()
            if (idInmueble == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "ID de inmueble inválido o faltante."))
                return@delete
            }
            try {
                val success = dao.deleteInmueble(idInmueble)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Inmueble eliminado exitosamente."))
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Inmueble no encontrado."))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al eliminar inmueble: ${e.message}"))
            }
        }


        // Obtener inmuebles por folio de parte de emergencia (GET /inmueble/folioPEmergencia/{folioPEmergencia})
        post("/crear-varios") {
            val request = call.receive<CreateInmueblesRequest>()

            // Verifica que el folioPEmergencia exista
            val parteEmergenciaExiste = dao.getParteEmergencia(request.folioPEmergencia) != null
            if (!parteEmergenciaExiste) {
                call.respond(HttpStatusCode.BadRequest, "El folioPEmergencia no existe.")
                return@post
            }

            // Mapea los datos recibidos al modelo Inmuebles
            val inmuebles = request.inmuebles.map { inmueble ->
                Inmuebles(
                    direccion = inmueble.direccion,
                    tipoInmueble = inmueble.tipoInmueble,
                    estadoInmueble = inmueble.estadoInmueble,
                    folioPEmergencia = request.folioPEmergencia
                )
            }

            // Llama al DAO para guardar los inmuebles
            val result = dao.createInmuebles(inmuebles, request.folioPEmergencia)

            // Devuelve la respuesta con los inmuebles creados
            call.respond(HttpStatusCode.Created, result)
        }
    }
}
